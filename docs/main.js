var chartColors = {
	red: 'rgb(255, 99, 132)',
	blue: 'rgb(54, 162, 235)'
};

var color = Chart.helpers.color;
Chart.defaults.global.elements.line.backgroundColor = 'transparent';
Chart.defaults.global.elements.point.borderColor = 'transparent';
Chart.defaults.global.elements.point.backgroundColor = 'transparent';

var yAxes= [{
				id: 'deces',
				beginAtZero: true,
				gridLines: {
					drawOnChartArea: false
				},
				scaleLabel: {
					display: true,
					labelString: 'Nombre de décès'
				}
			}, {
				id: 'taux',
				beginAtZero: true,
				position: 'right',
				gridLines: {
					drawOnChartArea: false
				},
				scaleLabel: {
					display: true,
					labelString: 'Taux (deces/1M d\'hab)'
				}
			}];

var configDecesParAn = {
	type: 'line',
	data: {
		datasets: [{
			hidden: true,
			yAxisID: 'deces',
			borderColor: chartColors.red
		}, {
			yAxisID: 'deces',
			borderColor: chartColors.red
		}, {
			hidden: true,
			yAxisID: 'taux',
			borderColor: chartColors.blue
		}, {
			yAxisID: 'taux',
			borderColor: chartColors.blue
		}]
	},
	plugins: [ChartDataSource],
	options: {
		scales: {
			xAxes: [{
				scaleLabel: {
					display: true,
					labelString: 'Annee'
				}
			}],
			yAxes: yAxes
		},
		plugins: {
			datasource: {
				type: 'csv',
				url: 'results/DecesParAn.csv',
				delimiter: ';',
				rowMapping: 'index',
				datasetLabels: true,
				indexLabels: true
			}
		}
	}
};

var configDecesParSemaine = {
	type: 'line',
	data: {
		datasets: [{
			hidden: true,
			yAxisID: 'deces',
			borderColor: chartColors.red,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		}, {
			yAxisID: 'deces',
			borderColor: chartColors.red,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		}, {
			yAxisID: 'deces',
			borderColor: chartColors.red,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		}, {
			hidden: true,
			yAxisID: 'taux',
			borderColor: chartColors.blue,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		}, {
			yAxisID: 'taux',
			borderColor: chartColors.blue,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		}]
	},
	plugins: [ChartDataSource],
	options: {
		scales: {
			xAxes: {
				scaleLabel: {
					display: true,
					labelString: 'semaine'
				}
			},
			yAxes: yAxes
		},
		plugins: {
			datasource: {
				type: 'csv',
				url: 'results/DecesParSemaine.csv',
				delimiter: ';',
				rowMapping: 'index',
				datasetLabels: true,
				indexLabels: true
			}
		}
	}
};

var configDecesParAge = {
	type: 'line',
	data: {
		datasets: [{
			hidden: false
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: false
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: false
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: true
		},{
			hidden: false,
			borderColor: chartColors.blue,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		},{
			hidden: false,
			borderColor: chartColors.red,
			pointBackgroundColor: 'transparent',
			pointBorderColor: 'transparent'
		},{
			hidden: true
		}]
	},
	plugins: [ChartDataSource],
	options: {
		scales: {
			xAxes: [{
				scaleLabel: {
					display: true,
					labelString: 'Age'
				}
			}],
			yAxes: [{
				id: 'taux',
				beginAtZero: true,
				type: 'logarithmic',
				gridLines: {
					drawOnChartArea: false
				},
				scaleLabel: {
					display: true,
					labelString: 'Taux (deces/1M d\'hab)'
				}
			}]
		},
		plugins: {
			datasource: {
				type: 'csv',
				url: 'results/DecesParAge.csv',
				delimiter: ';',
				rowMapping: 'index',
				datasetLabels: true,
				indexLabels: true
			}
		}
	}
};

window.onload = function() {
	var ctx = document.getElementById('DecesParAn').getContext('2d');
	new Chart(ctx, configDecesParAn);

	var ctx = document.getElementById('DecesParSemaine').getContext('2d');
	new Chart(ctx, configDecesParSemaine);

	var ctx = document.getElementById('DecesParAge').getContext('2d');
	new Chart(ctx, configDecesParAge);
	
	
const data = {
  labels: [
    '75+',
    '65-74',
    '45-64',
    '15-44',
    '0-14'
  ],
  datasets: [{
    label: 'nombre de décès lors d’une hospitalisation',
    data: [61659, 14862, 7166, 640, 8],
    backgroundColor: [
      'rgb(255, 99, 132)',
      'rgb(54, 162, 235)',
      'rgb(255, 205, 86)'
    ],
    hoverOffset: 4
  }]
};
  
    var ctx = document.getElementById('DecesCovidParAge').getContext('2d');
	new Chart(ctx, {
	  type: 'pie',
	  data: data,
	});

};

