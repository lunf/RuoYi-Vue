<template>
	<el-form size="small">
		<el-form-item>
			<el-radio :label="1" v-model='radioValue'>
				not filled，Allowed accessories.[, - * /]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio :label="2" v-model='radioValue'>
				Every year
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio :label="3" v-model='radioValue'>
				The cycle from
				<el-input-number v-model='cycle01' :min='fullYear' :max="2098" /> -
				<el-input-number v-model='cycle02' :min="cycle01 ? cycle01 + 1 : fullYear + 1" :max="2099" />
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio :label="4" v-model='radioValue'>
				from
				<el-input-number v-model='average01' :min='fullYear' :max="2098"/> The year begins，Every one
				<el-input-number v-model='average02' :min="1" :max="2099 - average01 || fullYear" /> Execution once a year.
			</el-radio>

		</el-form-item>

		<el-form-item>
			<el-radio :label="5" v-model='radioValue'>
				designated
				<el-select clearable v-model="checkboxList" placeholder="A lot of choice." multiple>
					<el-option v-for="item in 9" :key="item" :value="item - 1 + fullYear" :label="item -1 + fullYear" />
				</el-select>
			</el-radio>
		</el-form-item>
	</el-form>
</template>

<script>
export default {
	data() {
		return {
			fullYear: 0,
			radioValue: 1,
			cycle01: 0,
			cycle02: 0,
			average01: 0,
			average02: 1,
			checkboxList: [],
			checkNum: this.$options.propsData.check
		}
	},
	name: 'crontab-year',
	props: ['check', 'month', 'cron'],
	methods: {
		// When the button value changes.
		radioChange() {
			switch (this.radioValue) {
				case 1:
					this.$emit('update', 'year', '');
					break;
				case 2:
					this.$emit('update', 'year', '*');
					break;
				case 3:
					this.$emit('update', 'year', this.cycleTotal);
					break;
				case 4:
					this.$emit('update', 'year', this.averageTotal);
					break;
				case 5:
					this.$emit('update', 'year', this.checkboxString);
					break;
			}
		},
		// When two cycles change.
		cycleChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'year', this.cycleTotal);
			}
		},
		// Meanwhile two values change.
		averageChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'year', this.averageTotal);
			}
		},
		// checkboxWhen the value changes.
		checkboxChange() {
			if (this.radioValue == '5') {
				this.$emit('update', 'year', this.checkboxString);
			}
		}
	},
	watch: {
		'radioValue': 'radioChange',
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'checkboxString': 'checkboxChange'
	},
	computed: {
		// Calculate two cycle values.
		cycleTotal: function () {
			const cycle01 = this.checkNum(this.cycle01, this.fullYear, 2098)
			const cycle02 = this.checkNum(this.cycle02, cycle01 ? cycle01 + 1 : this.fullYear + 1, 2099)
			return cycle01 + '-' + cycle02;
		},
		// Calculate the average value.
		averageTotal: function () {
			const average01 = this.checkNum(this.average01, this.fullYear, 2098)
			const average02 = this.checkNum(this.average02, 1, 2099 - average01 || this.fullYear)
			return average01 + '/' + average02;
		},
		// Calculations are selected.checkboxValue of gathering
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str;
		}
	},
	mounted: function () {
		// Only the current year.
		this.fullYear = Number(new Date().getFullYear());
		this.cycle01 = this.fullYear
		this.average01 = this.fullYear
	}
}
</script>
