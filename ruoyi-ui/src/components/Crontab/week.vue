<template>
	<el-form size='small'>
		<el-form-item>
			<el-radio v-model='radioValue' :label="1">
				Weekly，Allowed accessories.[, - * ? / L #]
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="2">
				Not specified.
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="3">
				Cycle from week.
				<el-select clearable v-model="cycle01">
					<el-option
						v-for="(item,index) of weekList"
						:key="index"
						:label="item.value"
						:value="item.key"
						:disabled="item.key === 1"
					>{{item.value}}</el-option>
				</el-select>
				-
				<el-select clearable v-model="cycle02">
					<el-option
						v-for="(item,index) of weekList"
						:key="index"
						:label="item.value"
						:value="item.key"
						:disabled="item.key < cycle01 && item.key !== 1"
					>{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="4">
				The second
				<el-input-number v-model='average01' :min="1" :max="4" /> Weekly Weekly
				<el-select clearable v-model="average02">
					<el-option v-for="(item,index) of weekList" :key="index" :label="item.value" :value="item.key">{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="5">
				Last week of this month.
				<el-select clearable v-model="weekday">
					<el-option v-for="(item,index) of weekList" :key="index" :label="item.value" :value="item.key">{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

		<el-form-item>
			<el-radio v-model='radioValue' :label="6">
				designated
				<el-select clearable v-model="checkboxList" placeholder="A lot of choice." multiple style="width:100%">
					<el-option v-for="(item,index) of weekList" :key="index" :label="item.value" :value="String(item.key)">{{item.value}}</el-option>
				</el-select>
			</el-radio>
		</el-form-item>

	</el-form>
</template>

<script>
export default {
	data() {
		return {
			radioValue: 2,
			weekday: 2,
			cycle01: 2,
			cycle02: 3,
			average01: 1,
			average02: 2,
			checkboxList: [],
			weekList: [
				{
					key: 2,
					value: 'Monday'
				},
				{
					key: 3,
					value: 'Tuesday'
				},
				{
					key: 4,
					value: 'Wednesday'
				},
				{
					key: 5,
					value: 'Thursday'
				},
				{
					key: 6,
					value: 'Friday'
				},
				{
					key: 7,
					value: 'Saturday'
				},
				{
					key: 1,
					value: 'Sunday'
				}
			],
			checkNum: this.$options.propsData.check
		}
	},
	name: 'crontab-week',
	props: ['check', 'cron'],
	methods: {
		// When the button value changes.
		radioChange() {
			if (this.radioValue !== 2 && this.cron.day !== '?') {
				this.$emit('update', 'day', '?', 'week');
			}
			switch (this.radioValue) {
				case 1:
					this.$emit('update', 'week', '*');
					break;
				case 2:
					this.$emit('update', 'week', '?');
					break;
				case 3:
					this.$emit('update', 'week', this.cycleTotal);
					break;
				case 4:
					this.$emit('update', 'week', this.averageTotal);
					break;
				case 5:
					this.$emit('update', 'week', this.weekdayCheck + 'L');
					break;
				case 6:
					this.$emit('update', 'week', this.checkboxString);
					break;
			}
		},

		// When two cycles change.
		cycleChange() {
			if (this.radioValue == '3') {
				this.$emit('update', 'week', this.cycleTotal);
			}
		},
		// Meanwhile two values change.
		averageChange() {
			if (this.radioValue == '4') {
				this.$emit('update', 'week', this.averageTotal);
			}
		},
		// During recent changes in working hours.
		weekdayChange() {
			if (this.radioValue == '5') {
				this.$emit('update', 'week', this.weekday + 'L');
			}
		},
		// checkboxWhen the value changes.
		checkboxChange() {
			if (this.radioValue == '6') {
				this.$emit('update', 'week', this.checkboxString);
			}
		},
	},
	watch: {
		'radioValue': 'radioChange',
		'cycleTotal': 'cycleChange',
		'averageTotal': 'averageChange',
		'weekdayCheck': 'weekdayChange',
		'checkboxString': 'checkboxChange',
	},
	computed: {
		// Calculate two cycle values.
		cycleTotal: function () {
			this.cycle01 = this.checkNum(this.cycle01, 1, 7)
			this.cycle02 = this.checkNum(this.cycle02, 1, 7)
			return this.cycle01 + '-' + this.cycle02;
		},
		// Calculate the average value.
		averageTotal: function () {
			this.average01 = this.checkNum(this.average01, 1, 4)
			this.average02 = this.checkNum(this.average02, 1, 7)
			return this.average02 + '#' + this.average01;
		},
		// Last working day.（formats）
		weekdayCheck: function () {
			this.weekday = this.checkNum(this.weekday, 1, 7)
			return this.weekday;
		},
		// Calculations are selected.checkboxValue of gathering
		checkboxString: function () {
			let str = this.checkboxList.join();
			return str == '' ? '*' : str;
		}
	}
}
</script>
