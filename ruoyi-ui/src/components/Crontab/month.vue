<template>
	<el-form size='small'>
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				The Moon，Allowed accessories.[, - * /]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				The cycle from
				<el-input-number v-model='cycle01' :min="1" :max="11" /> -
				<el-input-number v-model='cycle02' :min="cycle01 ? cycle01 + 1 : 2" :max="12" /> The Moon
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				from
				<el-input-number v-model='average01' :min="1" :max="11" /> The month begins.，Every one
				<el-input-number v-model='average02' :min="1" :max="12 - average01 || 0" /> Execute once a month.
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				designated
				<el-select clearable v-model="checkboxList" placeholder="A lot of choice." multiple style="width:100%">
					<el-option v-for="item in 12" :key="item" :value="item">{{item}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>
	</el-form>
</template>

<script>
export default {
	data() {
		return {
			radioValue: 1,
			cycle01: 1,
			cycle02: 2,
			average01: 1,
			average02: 1,
			checkboxList: [],
			checkNum: this.check
		}
	},
	name: 'crontab-month',
	props: ['check', 'cron'],
	methods: {
		// When the button value changes.
		radioChange() {
			switch (this.radioValue) {
				case 1:
					this.$emit('update', 'month', '*');
					break;
				case 2:
					this.$emit('update', 'month', this.cycleTotal);
					break;
				case 3:
					this.$emit('update', 'month', this.averageTotal);
					break;
				case 4:
					this.$emit('update', 'month', this.checkboxString);
					break;
			}
		},
		// When two cycles change.
		cycleChange() {
			if (this.radioValue == '2') {
				this.$emit('update', 'month', this.cycleTotal);
			}
		},
		// Meanwhile two values change.
		averageChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'month', this.averageTotal);
			}
		},
		// checkboxWhen the value changes.
		checkboxChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'month', this.checkboxString);
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
			const cycle01 = this.checkNum(this.cycle01, 1, 11)
			const cycle02 = this.checkNum(this.cycle02, cycle01 ? cycle01 + 1 : 2, 12)
			return cycle01 + '-' + cycle02;
		},
		// Calculate the average value.
		averageTotal: function () {
			const average01 = this.checkNum(this.average01, 1, 11)
			const average02 = this.checkNum(this.average02, 1, 12 - average01 || 0)
			return average01 + '/' + average02;
		},
		// Calculations are selected.checkboxValue of gathering
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str == '' ? '*' : str;
		}
	}
}
</script>
