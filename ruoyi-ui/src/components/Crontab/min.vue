<template>
	<el-form size="small">
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				Minutes，Allowed accessories.[, - * /]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				The cycle from
				<el-input-number v-model='cycle01' :min="0" :max="58" /> -
				<el-input-number v-model='cycle02' :min="cycle01 ? cycle01 + 1 : 1" :max="59" /> Minutes
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				from
				<el-input-number v-model='average01' :min="0" :max="58" /> The minute begins.，Every one
				<el-input-number v-model='average02' :min="1" :max="59 - average01 || 0" /> Execute one minute.
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				designated
				<el-select clearable v-model="checkboxList" placeholder="A lot of choice." multiple style="width:100%">
					<el-option v-for="item in 60" :key="item" :value="item-1">{{item-1}}</el-option>
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
			average01: 0,
			average02: 1,
			checkboxList: [],
			checkNum: this.$options.propsData.check
		}
	},
	name: 'crontab-min',
	props: ['check', 'cron'],
	methods: {
		// When the button value changes.
		radioChange() {
			switch (this.radioValue) {
				case 1:
					this.$emit('update', 'min', '*', 'min');
					break;
				case 2:
					this.$emit('update', 'min', this.cycleTotal, 'min');
					break;
				case 3:
					this.$emit('update', 'min', this.averageTotal, 'min');
					break;
				case 4:
					this.$emit('update', 'min', this.checkboxString, 'min');
					break;
			}
		},
		// When two cycles change.
		cycleChange() {
			if (this.radioValue == '2') {
				this.$emit('update', 'min', this.cycleTotal, 'min');
			}
		},
		// Meanwhile two values change.
		averageChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'min', this.averageTotal, 'min');
			}
		},
		// checkboxWhen the value changes.
		checkboxChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'min', this.checkboxString, 'min');
			}
		},

	},
	watch: {
		'radioValue': 'radioChange',
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'checkboxString': 'checkboxChange',
	},
	computed: {
		// Calculate two cycle values.
		cycleTotal: function () {
			const cycle01 = this.checkNum(this.cycle01, 0, 58)
			const cycle02 = this.checkNum(this.cycle02, cycle01 ? cycle01 + 1 : 1, 59)
			return cycle01 + '-' + cycle02;
		},
		// Calculate the average value.
		averageTotal: function () {
			const average01 = this.checkNum(this.average01, 0, 58)
			const average02 = this.checkNum(this.average02, 1, 59 - average01 || 0)
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