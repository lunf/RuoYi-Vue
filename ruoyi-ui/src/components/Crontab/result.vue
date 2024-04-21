<template>
	<div class="popup-result">
		<p class="title">Recently5The next running time.</p>
		<ul class="popup-result-scroll">
			<template v-if='isShow'>
				<li v-for='item in resultList' :key="item">{{item}}</li>
			</template>
			<li v-else>In the results of calculation....</li>
		</ul>
	</div>
</template>

<script>
export default {
	data() {
		return {
			dayRule: '',
			dayRuleSup: '',
			dateArr: [],
			resultList: [],
			isShow: false
		}
	},
	name: 'crontab-result',
	methods: {
		// When the expression value changes，Start calculating the results.
		expressionChange() {

			// The calculation begins.-Hidden Results
			this.isShow = false;
			// Get the number of rules.[0Seconds、1Parts、2The time、3day、4The Moon、5Weekly、6Year]
			let ruleArr = this.$options.propsData.ex.split(' ');
			// Recording the number of times entering the cycle.
			let nums = 0;
			// The number of results used for temporary storage of the symbol time rule
			let resultArr = [];
			// Get the current time accurately.[Year、The Moon、day、The time、Parts、Seconds]
			let nTime = new Date();
			let nYear = nTime.getFullYear();
			let nMonth = nTime.getMonth() + 1;
			let nDay = nTime.getDate();
			let nHour = nTime.getHours();
			let nMin = nTime.getMinutes();
			let nSecond = nTime.getSeconds();
			// Accepted by rules.100Permanent number of years.、Monthly group and wait.
			this.getSecondArr(ruleArr[0]);
			this.getMinArr(ruleArr[1]);
			this.getHourArr(ruleArr[2]);
			this.getDayArr(ruleArr[3]);
			this.getMonthArr(ruleArr[4]);
			this.getWeekArr(ruleArr[5]);
			this.getYearArr(ruleArr[6], nYear);
			// The number that will be obtained.-Easy to use.
			let sDate = this.dateArr[0];
			let mDate = this.dateArr[1];
			let hDate = this.dateArr[2];
			let DDate = this.dateArr[3];
			let MDate = this.dateArr[4];
			let YDate = this.dateArr[5];
			// Get the index of current time in groups
			let sIdx = this.getIndex(sDate, nSecond);
			let mIdx = this.getIndex(mDate, nMin);
			let hIdx = this.getIndex(hDate, nHour);
			let DIdx = this.getIndex(DDate, nDay);
			let MIdx = this.getIndex(MDate, nMonth);
			let YIdx = this.getIndex(YDate, nYear);
			// Repeat the functions of the month.(More in the back.)
			const resetSecond = function () {
				sIdx = 0;
				nSecond = sDate[sIdx]
			}
			const resetMin = function () {
				mIdx = 0;
				nMin = mDate[mIdx]
				resetSecond();
			}
			const resetHour = function () {
				hIdx = 0;
				nHour = hDate[hIdx]
				resetMin();
			}
			const resetDay = function () {
				DIdx = 0;
				nDay = DDate[DIdx]
				resetHour();
			}
			const resetMonth = function () {
				MIdx = 0;
				nMonth = MDate[MIdx]
				resetDay();
			}
			// If the current year is not the current value of the group.
			if (nYear !== YDate[YIdx]) {
				resetMonth();
			}
			// If the current month is not the current value of the group.
			if (nMonth !== MDate[MIdx]) {
				resetDay();
			}
			// If Current“day”Not the current value of the group.
			if (nDay !== DDate[DIdx]) {
				resetHour();
			}
			// If Current“The time”Not the current value of the group.
			if (nHour !== hDate[hIdx]) {
				resetMin();
			}
			// If Current“Parts”Not the current value of the group.
			if (nMin !== mDate[mIdx]) {
				resetSecond();
			}

			// Cycle number of years.
			goYear: for (let Yi = YIdx; Yi < YDate.length; Yi++) {
				let YY = YDate[Yi];
				// When the maximum value is reached
				if (nMonth > MDate[MDate.length - 1]) {
					resetMonth();
					continue;
				}
				// Circular Months
				goMonth: for (let Mi = MIdx; Mi < MDate.length; Mi++) {
					// The value、Easy to operate behind.
					let MM = MDate[Mi];
					MM = MM < 10 ? '0' + MM : MM;
					// When the maximum value is reached
					if (nDay > DDate[DDate.length - 1]) {
						resetDay();
						if (Mi == MDate.length - 1) {
							resetMonth();
							continue goYear;
						}
						continue;
					}
					// Number of cycle dates
					goDay: for (let Di = DIdx; Di < DDate.length; Di++) {
						// The value、Easy to operate behind.
						let DD = DDate[Di];
						let thisDD = DD < 10 ? '0' + DD : DD;

						// When the maximum value is reached
						if (nHour > hDate[hDate.length - 1]) {
							resetHour();
							if (Di == DDate.length - 1) {
								resetDay();
								if (Mi == MDate.length - 1) {
									resetMonth();
									continue goYear;
								}
								continue goMonth;
							}
							continue;
						}

						// The legality of the date.，It is illegal to jump out of the current cycle.
						if (this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true && this.dayRule !== 'workDay' && this.dayRule !== 'lastWeek' && this.dayRule !== 'lastDay') {
							resetDay();
							continue goMonth;
						}
						// If there is a value in the date rule
						if (this.dayRule == 'lastDay') {
							// If it is not a legitimate date, it is necessary to transfer the date to the legitimate date, the last day of the end of the month.

							if (this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
								while (DD > 0 && this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
									DD--;

									thisDD = DD < 10 ? '0' + DD : DD;
								}
							}
						} else if (this.dayRule == 'workDay') {
							// Examination and adjustment if2The Moon30This date must be adjusted to the normal end of the month.
							if (this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
								while (DD > 0 && this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
									DD--;
									thisDD = DD < 10 ? '0' + DD : DD;
								}
							}
							// The date for obtaining the conditions is a week.X
							let thisWeek = this.formatDate(new Date(YY + '-' + MM + '-' + thisDD + ' 00:00:00'), 'week');
							// When Sunday
							if (thisWeek == 1) {
								// Find the next day.，To determine if it is the end of the month.
								DD++;
								thisDD = DD < 10 ? '0' + DD : DD;
								// The next day is no longer a legal date.
								if (this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
									DD -= 3;
								}
							} else if (thisWeek == 7) {
								// The Week6Just judge not.1Number can be operated.
								if (this.dayRuleSup !== 1) {
									DD--;
								} else {
									DD += 2;
								}
							}
						} else if (this.dayRule == 'weekDay') {
							// If it is scheduled for a week.
							// The current date is the week.
							let thisWeek = this.formatDate(new Date(YY + '-' + MM + '-' + DD + ' 00:00:00'), 'week');
							// Check whether this week is in the basement.（dayRuleSup）in
							if (this.dayRuleSup.indexOf(thisWeek) < 0) {
								// When the maximum value is reached
								if (Di == DDate.length - 1) {
									resetDay();
									if (Mi == MDate.length - 1) {
										resetMonth();
										continue goYear;
									}
									continue goMonth;
								}
								continue;
							}
						} else if (this.dayRule == 'assWeek') {
							// If determined is the week of the week.
							// Get each month.1It belongs to the week.
							let thisWeek = this.formatDate(new Date(YY + '-' + MM + '-' + DD + ' 00:00:00'), 'week');
							if (this.dayRuleSup[1] >= thisWeek) {
								DD = (this.dayRuleSup[0] - 1) * 7 + this.dayRuleSup[1] - thisWeek + 1;
							} else {
								DD = this.dayRuleSup[0] * 7 + this.dayRuleSup[1] - thisWeek + 1;
							}
						} else if (this.dayRule == 'lastWeek') {
							// If the last week of the month is determined.
							// Examination and adjustment if2The Moon30This date must be adjusted to the normal end of the month.
							if (this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
								while (DD > 0 && this.checkDate(YY + '-' + MM + '-' + thisDD + ' 00:00:00') !== true) {
									DD--;
									thisDD = DD < 10 ? '0' + DD : DD;
								}
							}
							// The last day of the month is Sunday.
							let thisWeek = this.formatDate(new Date(YY + '-' + MM + '-' + thisDD + ' 00:00:00'), 'week');
							// Find the last week in request.
							if (this.dayRuleSup < thisWeek) {
								DD -= thisWeek - this.dayRuleSup;
							} else if (this.dayRuleSup > thisWeek) {
								DD -= 7 - (this.dayRuleSup - thisWeek)
							}
						}
						// determine whether the time value is less than10Replaced by“05”This format.
						DD = DD < 10 ? '0' + DD : DD;

						// The Circle“The time”Number of groups
						goHour: for (let hi = hIdx; hi < hDate.length; hi++) {
							let hh = hDate[hi] < 10 ? '0' + hDate[hi] : hDate[hi]

							// When the maximum value is reached
							if (nMin > mDate[mDate.length - 1]) {
								resetMin();
								if (hi == hDate.length - 1) {
									resetHour();
									if (Di == DDate.length - 1) {
										resetDay();
										if (Mi == MDate.length - 1) {
											resetMonth();
											continue goYear;
										}
										continue goMonth;
									}
									continue goDay;
								}
								continue;
							}
							// The Circle"Parts"Number of groups
							goMin: for (let mi = mIdx; mi < mDate.length; mi++) {
								let mm = mDate[mi] < 10 ? '0' + mDate[mi] : mDate[mi];

								// When the maximum value is reached
								if (nSecond > sDate[sDate.length - 1]) {
									resetSecond();
									if (mi == mDate.length - 1) {
										resetMin();
										if (hi == hDate.length - 1) {
											resetHour();
											if (Di == DDate.length - 1) {
												resetDay();
												if (Mi == MDate.length - 1) {
													resetMonth();
													continue goYear;
												}
												continue goMonth;
											}
											continue goDay;
										}
										continue goHour;
									}
									continue;
								}
								// The Circle"Seconds"Number of groups
								goSecond: for (let si = sIdx; si <= sDate.length - 1; si++) {
									let ss = sDate[si] < 10 ? '0' + sDate[si] : sDate[si];
									// Add the current time.（Time legality has been judged during the date cycle.）
									if (MM !== '00' && DD !== '00') {
										resultArr.push(YY + '-' + MM + '-' + DD + ' ' + hh + ':' + mm + ':' + ss)
										nums++;
									}
									// If the number is full, leave the circle.
									if (nums == 5) break goYear;
									// When the maximum value is reached
									if (si == sDate.length - 1) {
										resetSecond();
										if (mi == mDate.length - 1) {
											resetMin();
											if (hi == hDate.length - 1) {
												resetHour();
												if (Di == DDate.length - 1) {
													resetDay();
													if (Mi == MDate.length - 1) {
														resetMonth();
														continue goYear;
													}
													continue goMonth;
												}
												continue goDay;
											}
											continue goHour;
										}
										continue goMin;
									}
								} //goSecond
							} //goMin
						}//goHour
					}//goDay
				}//goMonth
			}
			// judgment100Number of results in the year.
			if (resultArr.length == 0) {
				this.resultList = ['Not achieving the conditions.！'];
			} else {
				this.resultList = resultArr;
				if (resultArr.length !== 5) {
					this.resultList.push('Recently100Only above in the year.' + resultArr.length + 'Article Results！')
				}
			}
			// Calculation is completed.-Showing the results.
			this.isShow = true;


		},
		// It is used to calculate the index of a number in a group.
		getIndex(arr, value) {
			if (value <= arr[0] || value > arr[arr.length - 1]) {
				return 0;
			} else {
				for (let i = 0; i < arr.length - 1; i++) {
					if (value > arr[i] && value <= arr[i + 1]) {
						return i + 1;
					}
				}
			}
		},
		// obtained"Year"Number of groups
		getYearArr(rule, year) {
			this.dateArr[5] = this.getOrderArr(year, year + 100);
			if (rule !== undefined) {
				if (rule.indexOf('-') >= 0) {
					this.dateArr[5] = this.getCycleArr(rule, year + 100, false)
				} else if (rule.indexOf('/') >= 0) {
					this.dateArr[5] = this.getAverageArr(rule, year + 100)
				} else if (rule !== '*') {
					this.dateArr[5] = this.getAssignArr(rule)
				}
			}
		},
		// obtained"The Moon"Number of groups
		getMonthArr(rule) {
			this.dateArr[4] = this.getOrderArr(1, 12);
			if (rule.indexOf('-') >= 0) {
				this.dateArr[4] = this.getCycleArr(rule, 12, false)
			} else if (rule.indexOf('/') >= 0) {
				this.dateArr[4] = this.getAverageArr(rule, 12)
			} else if (rule !== '*') {
				this.dateArr[4] = this.getAssignArr(rule)
			}
		},
		// obtained"day"Number of groups-Mainly on the date rules.
		getWeekArr(rule) {
			// Only the two values of the date rule are“”The date of expression is an option.
			if (this.dayRule == '' && this.dayRuleSup == '') {
				if (rule.indexOf('-') >= 0) {
					this.dayRule = 'weekDay';
					this.dayRuleSup = this.getCycleArr(rule, 7, false)
				} else if (rule.indexOf('#') >= 0) {
					this.dayRule = 'assWeek';
					let matchRule = rule.match(/[0-9]{1}/g);
					this.dayRuleSup = [Number(matchRule[1]), Number(matchRule[0])];
					this.dateArr[3] = [1];
					if (this.dayRuleSup[1] == 7) {
						this.dayRuleSup[1] = 0;
					}
				} else if (rule.indexOf('L') >= 0) {
					this.dayRule = 'lastWeek';
					this.dayRuleSup = Number(rule.match(/[0-9]{1,2}/g)[0]);
					this.dateArr[3] = [31];
					if (this.dayRuleSup == 7) {
						this.dayRuleSup = 0;
					}
				} else if (rule !== '*' && rule !== '?') {
					this.dayRule = 'weekDay';
					this.dayRuleSup = this.getAssignArr(rule)
				}
			}
		},
		// obtained"day"Number of groups-A small number of dates.
		getDayArr(rule) {
			this.dateArr[3] = this.getOrderArr(1, 31);
			this.dayRule = '';
			this.dayRuleSup = '';
			if (rule.indexOf('-') >= 0) {
				this.dateArr[3] = this.getCycleArr(rule, 31, false)
				this.dayRuleSup = 'null';
			} else if (rule.indexOf('/') >= 0) {
				this.dateArr[3] = this.getAverageArr(rule, 31)
				this.dayRuleSup = 'null';
			} else if (rule.indexOf('W') >= 0) {
				this.dayRule = 'workDay';
				this.dayRuleSup = Number(rule.match(/[0-9]{1,2}/g)[0]);
				this.dateArr[3] = [this.dayRuleSup];
			} else if (rule.indexOf('L') >= 0) {
				this.dayRule = 'lastDay';
				this.dayRuleSup = 'null';
				this.dateArr[3] = [31];
			} else if (rule !== '*' && rule !== '?') {
				this.dateArr[3] = this.getAssignArr(rule)
				this.dayRuleSup = 'null';
			} else if (rule == '*') {
				this.dayRuleSup = 'null';
			}
		},
		// obtained"The time"Number of groups
		getHourArr(rule) {
			this.dateArr[2] = this.getOrderArr(0, 23);
			if (rule.indexOf('-') >= 0) {
				this.dateArr[2] = this.getCycleArr(rule, 24, true)
			} else if (rule.indexOf('/') >= 0) {
				this.dateArr[2] = this.getAverageArr(rule, 23)
			} else if (rule !== '*') {
				this.dateArr[2] = this.getAssignArr(rule)
			}
		},
		// obtained"Parts"Number of groups
		getMinArr(rule) {
			this.dateArr[1] = this.getOrderArr(0, 59);
			if (rule.indexOf('-') >= 0) {
				this.dateArr[1] = this.getCycleArr(rule, 60, true)
			} else if (rule.indexOf('/') >= 0) {
				this.dateArr[1] = this.getAverageArr(rule, 59)
			} else if (rule !== '*') {
				this.dateArr[1] = this.getAssignArr(rule)
			}
		},
		// obtained"Seconds"Number of groups
		getSecondArr(rule) {
			this.dateArr[0] = this.getOrderArr(0, 59);
			if (rule.indexOf('-') >= 0) {
				this.dateArr[0] = this.getCycleArr(rule, 60, true)
			} else if (rule.indexOf('/') >= 0) {
				this.dateArr[0] = this.getAverageArr(rule, 59)
			} else if (rule !== '*') {
				this.dateArr[0] = this.getAssignArr(rule)
			}
		},
		// According to the entry.min-maxReturn to a number of orders.
		getOrderArr(min, max) {
			let arr = [];
			for (let i = min; i <= max; i++) {
				arr.push(i);
			}
			return arr;
		},
		// Returns a number according to the separated value specified in the rule
		getAssignArr(rule) {
			let arr = [];
			let assiginArr = rule.split(',');
			for (let i = 0; i < assiginArr.length; i++) {
				arr[i] = Number(assiginArr[i])
			}
			arr.sort(this.compare)
			return arr;
		},
		// According to a certain calculation rule, a number is calculated back.
		getAverageArr(rule, limit) {
			let arr = [];
			let agArr = rule.split('/');
			let min = Number(agArr[0]);
			let step = Number(agArr[1]);
			while (min <= limit) {
				arr.push(min);
				min += step;
			}
			return arr;
		},
		// Returning a cyclic group according to the rule.
		getCycleArr(rule, limit, status) {
			// status--It says that from0Started（and from1Started）
			let arr = [];
			let cycleArr = rule.split('-');
			let min = Number(cycleArr[0]);
			let max = Number(cycleArr[1]);
			if (min > max) {
				max += limit;
			}
			for (let i = min; i <= max; i++) {
				let add = 0;
				if (status == false && i % limit == 0) {
					add = limit;
				}
				arr.push(Math.round(i % limit + add))
			}
			arr.sort(this.compare)
			return arr;
		},
		// Comparing the size of the number.（usedArray.sort）
		compare(value1, value2) {
			if (value2 - value1 > 0) {
				return -1;
			} else {
				return 1;
			}
		},
		// Format date formats as：2017-9-19 18:04:33
		formatDate(value, type) {
			// Calculation of date related values
			let time = typeof value == 'number' ? new Date(value) : value;
			let Y = time.getFullYear();
			let M = time.getMonth() + 1;
			let D = time.getDate();
			let h = time.getHours();
			let m = time.getMinutes();
			let s = time.getSeconds();
			let week = time.getDay();
			// If transmitted.typeThe word
			if (type == undefined) {
				return Y + '-' + (M < 10 ? '0' + M : M) + '-' + (D < 10 ? '0' + D : D) + ' ' + (h < 10 ? '0' + h : h) + ':' + (m < 10 ? '0' + m : m) + ':' + (s < 10 ? '0' + s : s);
			} else if (type == 'week') {
				// inquartzin 1for Sunday.
				return week + 1;
			}
		},
		// Check whether the date exists.
		checkDate(value) {
			let time = new Date(value);
			let format = this.formatDate(time)
			return value === format;
		}
	},
	watch: {
		'ex': 'expressionChange'
	},
	props: ['ex'],
	mounted: function () {
		// Initiation Get one result.
		this.expressionChange();
	}
}

</script>
