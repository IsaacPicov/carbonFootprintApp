package com.example.b07demosummer2024;


public class Constants {
//    ALL OF THIS IS SURVEY STUFF
    public static final int YES_OPTION = 1;
    public static final int NO_OPTION = 0;

    public static final double GAS_VALUE = 0.24;
    public static final double DIESEL_VALUE = 0.27;
    public static final double HYBRID_VALUE = 0.16;
    public static final double ELECTRIC_VALUE = 0.05;
    public static final double DEFAULT_FUEL_VALUE = GAS_VALUE;

    public static final double UP_TO_5000KM = 5000.0;
    public static final double KM_5000_10000 = 10000.0;
    public static final double KM_10000_15000 = 15000.0;
    public static final double KM_15000_20000 = 20000.0;
    public static final double KM_20000_25000 = 25000.0;
    public static final double DEFAULT_DISTANCE = 35000.0;

    public static final double NEVER_OPTION = 0.0;
    public static final double OCCASIONAL_OPTION = 1.0;
    public static final double FREQUENT_OPTION = 2.0;
    public static final double ALWAYS_OPTION = 3.0;

    public static final double UNDER_1_HOUR_OCCASIONAL = 246.0;
    public static final double UNDER_1_HOUR_FREQUENT = 573.0;
    public static final double HOUR_1_3_OCCASIONAL = 819.0;
    public static final double HOUR_1_3_FREQUENT = 1911.0;
    public static final double HOUR_3_5_OCCASIONAL = 1638.0;
    public static final double HOUR_3_5_FREQUENT = 3822.0;
    public static final double HOUR_5_10_OCCASIONAL = 3071.0;
    public static final double HOUR_5_10_FREQUENT = 7166.0;
    public static final double ABOVE_10_HOURS_OCCASIONAL = 4095.0;
    public static final double ABOVE_10_HOURS_FREQUENT = 9555.0;

    public static final double FLIGHTS_1_2_SHORT = 255.0;
    public static final double FLIGHTS_3_5_SHORT = 600.0;
    public static final double FLIGHTS_6_10_SHORT = 1200.0;
    public static final double FLIGHTS_10_PLUS = 1800.0;

    public static final double FLIGHTS_1_2_LONG = 825.0;
    public static final double FLIGHTS_3_5_LONG = 2200.0;
    public static final double FLIGHTS_6_10_LONG = 4400.0;
    public static final double FLIGHTS_10_LONG = 6600.0;

    public static final double VEGETARIAN = 1000.0;
    public static final double VEGAN = 500.0;
    public static final double PESCATARIAN = 1500.0;
    public static final double MEATBASED = 0;

    public static final double BEEF_DAILY = 2500.0;
    public static final double BEEF_FREQUENT = 1900.0;
    public static final double BEEF_OCCASIONALLY = 1300.0;

    public static final double PORK_DAILY = 1450.0;
    public static final double PORK_FREQUENT = 860.0;
    public static final double PORK_OCCASIONALLY = 450.0;

    public static final double CHICKEN_DAILY = 950.0;
    public static final double CHICKEN_FREQUENTLY = 600.0;
    public static final double CHICKEN_OCCASIONALLY = 150.0;

    public static final double FISH_DAILY = 800.0;
    public static final double FISH_FREQUENTLY = 500.0;
    public static final double FISH_OCCASIONALLY = 150.0;

    public static final double FOOD_WASTE_RARELY = 23.4;
    public static final double FOOD_WASTE_OCCASIONALLY = 70.2;
    public static final double FOOD_WASTE_FREQUENTLY = 140.4;
    public static final double KGtoTONSCONSTANT = 907.2;



//    Housing data stuff
//    Size of house, monthly electricity bill,type of energy, number of occupants
public static final double[][][][][] housingData = {
        {  //detached house
                {  //1 person
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2400,  //natural gas
                                        200,  //electricity
                                        2100,  //oil
                                        2870,  //propane
                                        2170  //wood
                                },
                                {  //$50-100/month
                                        2440,  //natural gas
                                        400,  //electricity
                                        5200,  //oil
                                        4400,  //propane
                                        2340  //wood
                                },
                                {  //$100-150/month
                                        2610,  //natural gas
                                        1200,  //electricity
                                        6100,  //oil
                                        5400,  //propane
                                        2510  //wood
                                },
                                {  //$150-200/month
                                        2780,  //natural gas
                                        1700,  //electricity
                                        7200,  //oil
                                        6400,  //propane
                                        2680  //wood
                                },
                                {  //>$200/month
                                        3100,  //natural gas
                                        2300,  //electricity
                                        8200,  //oil
                                        7400,  //propane
                                        3000  //wood
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2800,  //natural gas
                                        300,  //electricity
                                        3800,  //oil
                                        3770,  //propane
                                        3670  //wood
                                },
                                {  //$50-100/month
                                        5900,  //natural gas
                                        600,  //electricity
                                        5300,  //oil
                                        3940,  //propane
                                        3840  //wood
                                },
                                {  //$100-150/month
                                        6500,  //natural gas
                                        1200,  //electricity
                                        6200,  //oil
                                        7100,  //propane
                                        4010  //wood
                                },
                                {  //$150-200/month
                                        7100,  //natural gas
                                        1800,  //electricity
                                        7200,  //oil
                                        4280,  //propane
                                        4180  //wood
                                },
                                {  //>$200/month
                                        8300,  //natural gas
                                        2400,  //electricity
                                        8200,  //oil
                                        4600,  //propane
                                        4500  //wood
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        2800,  //natural gas
                                        320,  //electricity
                                        5400,  //oil
                                        5570,  //propane
                                        4170  //wood
                                },
                                {  //$50-100/month
                                        3000,  //natural gas
                                        600,  //electricity
                                        10500,  //oil
                                        5740,  //propane
                                        4340  //wood
                                },
                                {  //$100-150/month
                                        3200,  //natural gas
                                        1800,  //electricity
                                        14000,  //oil
                                        5800,  //propane
                                        4510  //wood
                                },
                                {  //$150-200/month
                                        3400,  //natural gas
                                        2700,  //electricity
                                        17500,  //oil
                                        5852,  //propane
                                        4680  //wood
                                },
                                {  //>$200/month
                                        3600,  //natural gas
                                        3600,  //electricity
                                        21000,  //oil
                                        6100,  //propane
                                        5000  //wood
                                }}},
                {  //2 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2600,  //natural gas
                                        250,  //electricity
                                        2650,  //oil
                                        3470,  //propane
                                        2370  //wood
                                },
                                {  //$50-100/month
                                        2640,  //natural gas
                                        500,  //electricity
                                        5400,  //oil
                                        4600,  //propane
                                        2540  //wood
                                },
                                {  //$100-150/month
                                        2810,  //natural gas
                                        1450,  //electricity
                                        6250,  //oil
                                        5600,  //propane
                                        2710  //wood
                                },
                                {  //$150-200/month
                                        2980,  //natural gas
                                        1900,  //electricity
                                        7400,  //oil
                                        6600,  //propane
                                        2880  //wood
                                },
                                {  //>$200/month
                                        3100,  //natural gas
                                        2500,  //electricity
                                        8400,  //oil
                                        7600,  //propane
                                        3200  //wood
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3000,  //natural gas
                                        380,  //electricity
                                        4000,  //oil
                                        4470,  //propane
                                        4170  //wood
                                },
                                {  //$50-100/month
                                        6100,  //natural gas
                                        800,  //electricity
                                        5440,  //oil
                                        4640,  //propane
                                        4340  //wood
                                },
                                {  //$100-150/month
                                        6700,
                                        1450,
                                        6400,
                                        7230,
                                        4510
                                },
                                {  //$150-200/month
                                        7300,
                                        2000,
                                        7400,
                                        4980,
                                        4680
                                },
                                {  //>$200/month
                                        8500,
                                        2600,
                                        8400,
                                        5300,
                                        5000
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        2880,
                                        450,
                                        6200,
                                        6170,
                                        4670
                                },
                                {  //$50-100/month
                                        3200,
                                        900,
                                        11000,
                                        6340,
                                        4840
                                },
                                {  //$100-150/month
                                        3400,
                                        2100,
                                        15500,
                                        6410,
                                        5010
                                },
                                {  //$150-200/month
                                        3600,
                                        3100,
                                        18100,
                                        6560,
                                        5180
                                },
                                {  //>$200/month
                                        3800,
                                        3800,
                                        22000,
                                        6840,
                                        5500
                                }}},
                {  //3-4 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2700,
                                        380,
                                        3200,
                                        4370,
                                        2670
                                },
                                {  //$50-100/month
                                        2940,
                                        550,
                                        5700,
                                        4900,
                                        2840
                                },
                                {  //$100-150/month
                                        3110,
                                        1600,
                                        6700,
                                        5900,
                                        3010
                                },
                                {  //$150-200/month
                                        3280,	2050,	7700,	6900,	3180
                                },
                                {  //>$200/month
                                        3600,	2700,	8700,	7900,	3500
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3380,	500,	4700,	5670,	4870
                                },
                                {  //$50-100/month
                                        6400,	1050,	5800,	5740,	5040
                                },
                                {  //$100-150/month
                                        7000,	1600,	6700,	7400,	5210
                                },
                                {  //$150-200/month
                                        7600,	2250,	7700,	5985,	5380
                                },
                                {  //>$200/month
                                        8800,	280,	8700,	6350,	5750
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3000,	520,	7000,	6970,	5270
                                },
                                {  //$50-100/month
                                        3500,	1500,	12500,	7240,	5640
                                },
                                {  //$100-150/month
                                        3700,	2300,	16250, 7300,	5710  //missing propane
                                },
                                {  //$150-200/month
                                        4100,	3400,	20000, 7600,	5980  //missing propane
                                },
                                {  //>$200/month
                                        4100,	4000,	23500, 7890,	6250  //missing propane
                                }}},
                {  //5 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        3000,	450,	3700,	5270,	2970
                                },
                                {  //$50-100/month
                                        3240,	600,	6000,	5200,	3140
                                },
                                {  //$100-150/month
                                        3410,	1800,	6950,	6200,	3310
                                },
                                {  //$150-200/month
                                        3580,	2200,	8000,	7200,	3480
                                },
                                {  //>$200/month
                                        3900,	3000,	9000,	8200,	3800
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3860,	600,	5350,	6570,	5670
                                },
                                {  //$50-100/month
                                        6700,	1200,	6100,	6740,	5840
                                },
                                {  //$100-150/month
                                        7300,	1800,	7000,	7550,	6010
                                },
                                {  //$150-200/month
                                        7900,	2400,	8000,	7080,	6180
                                },
                                {  //>$200/month
                                        9100,	3100,	9000,	7400,	6500
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3230,	675,	8900,	7970,	6170
                                },
                                {  //$50-100/month
                                        3800,	1800,	14000,	8140,	6340
                                },
                                {  //$100-150/month
                                        4000,	2700,	17500, 8230,	6510  //missing propane
                                },
                                {  //$150-200/month
                                        4400,	3600,	21000, 8300,	6680  //missing propane
                                },
                                {  //>$200/month
                                        4400,	4200,	25000, 8710,	7000  //missing propane
                                }}}},
        {  //semi
                {  //1 person
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2160,	300,	2500,	2200,	2100
                                },
                                {  //$50-100/month
                                        2400,	410,	2800,	2600,	3000
                                },
                                {  //$100-150/month
                                        2600,	1210,	3000,	2800,	3200
                                },
                                {  //$150-200/month
                                        2800,	1700,	3200,	3000,	3400
                                },
                                {  //>$200/month
                                        3000,	2200,	3400,	3200,	3600
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2443,	300,	3400,	4000,	1500
                                },
                                {  //$50-100/month
                                        4000,	600,	4000,	5000,	2500
                                },
                                {  //$100-150/month
                                        4500,	1200,	6100,	7000,	4100
                                },
                                {  //$150-200/month
                                        5000,	1800,	8000,	9000,	5500
                                },
                                {  //>$200/month
                                        6000,	2400,	10550,	12000,	7220
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        2821,	300,	3820,	4370,	3970
                                },
                                {  //$50-100/month
                                        7500,	1200,	5500,	4540,	4140
                                },
                                {  //$100-150/month
                                        10000,	1800,	8500,	4710,	4310
                                },
                                {  //$150-200/month
                                        12500,	2400,	11000,	4880,	4480
                                },
                                {  //>$200/month
                                        15000,	3000,	14000,	5200,	4800
                                }}},
                {  //2 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2349,	410, 2592,	2300,	2450
                                },
                                {  //$50-100/month
                                        2600,	500,	3000,	2800,	3200
                                },
                                {  //$100-150/month
                                        2800,	1450,	3200,	3000,	3400
                                },
                                {  //$150-200/month
                                        3000,	1900,	3400,	3200,	3600
                                },
                                {  //>$200/month
                                        3200,	2500,	3600,	3400,	3800
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2727,	410,	3499,	4300,	1800
                                },
                                {  //$50-100/month
                                        5200,	800,	4600,	6200,	2700
                                },
                                {  //$100-150/month
                                        6000,	1550,	6900,	8000,	4300
                                },
                                {  //$150-200/month
                                        6500,	2000,	8800,	10200,	6000
                                },
                                {  //>$200/month
                                        7800,	2500,	10900,	13200,	8000
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3010,	560,	4000,	4870,	4470
                                },
                                {  //$50-100/month
                                        8800,	1400,	6000,	5040,	4640
                                },
                                {  //$100-150/month
                                        12000,	2000,	9200,	5210,	4810
                                },
                                {  //$150-200/month
                                        14200,	2600,	12000,	5380,	4980
                                },
                                {  //>$200/month
                                        16800,	3500,	14800,	5700,	5300
                                }}},
                {  //3-4 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2732,	500, 2680,	2450,	2700
                                },
                                {  //$50-100/month
                                        2900,	560,	3300,	3100,	3500
                                },
                                {  //$100-150/month
                                        3100,	1620,	3500,	3300,	3700
                                },
                                {  //$150-200/month
                                        3300,	2000,	3700,	3500,	3900
                                },
                                {  //>$200/month
                                        3500,	2600,	3900,	3700,	4100
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3151,	550,	3599,	4700,	2100
                                },
                                {  //$50-100/month
                                        6800,	1050,	5100,	7300,	3500
                                },
                                {  //$100-150/month
                                        7800,	1700,	7300,	9100,	4850
                                },
                                {  //$150-200/month
                                        8800,	2250,	9200,	11000,	6800
                                },
                                {  //>$200/month
                                        9800,	2800,	11200,	14100,	8600
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3261,	890, 4307,	5670,	5270
                                },
                                {  //$50-100/month
                                        10800,	1650,	7200,	5840,	5340
                                },
                                {  //$100-150/month
                                        14000,	2300,	10200,	6010,	5610
                                },
                                {  //$150-200/month
                                        16000,	2820,	13500,	6180,	5780
                                },
                                {  //>$200/month
                                        18200,	4000,	15500,	6500,	6150
                                }}},
                {  //5 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        3199,	580, 2750,	2600,	3000
                                },
                                {  //$50-100/month
                                        3200,	600,	3600,	3400,	3800
                                },
                                {  //$100-150/month
                                        3400,	1820,	3800,	3600,	4000
                                },
                                {  //$150-200/month
                                        3600,	2200,	4000,	3800,	4200
                                },
                                {  //>$200/month
                                        3800,	3000,	4200,	4000,	4400
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3578,	605,	3700,	4900,	2500
                                },
                                {  //$50-100/month
                                        7500,	1200,	6000,	8000,	4000
                                },
                                {  //$100-150/month
                                        8500,	1800,	8500,	10000,	5500
                                },
                                {  //$150-200/month
                                        10000,	2400,	10500,	12000,	7100
                                },
                                {  //>$200/month
                                        11000,	3200,	12000,	15000,	9100
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3578,	1000,	4400,	6370,	5970
                                },
                                {  //$50-100/month
                                        12500,	1800,	8500,	6540,	6140
                                },
                                {  //$100-150/month
                                        15000,	2400,	11000,	6710,	6310
                                },
                                {  //$150-200/month
                                        17500,	3000,	14000,	6880,	6480
                                },
                                {  //>$200/month
                                        19000,	4500,	16000,	7200,	6800
                                }}}},
        {  //townhouse/other
                {  //1 person
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        1971,	300,	2400,	1500,	2000
                                },
                                {  //$50-100/month
                                        2800,	500,	2800,	2500,	2800
                                },
                                {  //$100-150/month
                                        3000,	1000,	3600,	3000,	3000
                                },
                                {  //$150-200/month
                                        4000,	1600,	4500,	3700,	3330
                                },
                                {  //>$200/month
                                        8000,	2000,	5000,	5800,	3500
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2443,	300, 2590,	3170,	1400
                                },
                                {  //$50-100/month
                                        4000,	550,	3800,	5600,	2400
                                },
                                {  //$100-150/month
                                        4300,	1200,	5000,	6000,	4000
                                },
                                {  //$150-200/month
                                        4800,	1700,	5350,	3680,	3800
                                },
                                {  //>$200/month
                                        9500,	2500,	5370,	6000,	4000
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        2822, 300,	2810,	3340,	3800
                                },
                                {  //$50-100/month
                                        3600,	1200,	4300,	5900,	3500
                                },
                                {  //$100-150/month
                                        5000,	1800,	5300,	3510,	4100
                                },
                                {  //$150-200/month
                                        8000,	2400,	5440,	3800,	4200
                                },
                                {  //>$200/month
                                        9500,	2800,	5670,	6200,	4300
                                }}},
                {  //2 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2160,	410,	2523,	1850,	2250
                                },
                                {  //$50-100/month
                                        2910,	550,	3100,	2800,	3000
                                },
                                {  //$100-150/month
                                        3210,	1250,	3750,	3500,	3300
                                },
                                {  //$150-200/month
                                        5500,	1750,	4600,	4500,	3500
                                },
                                {  //>$200/month
                                        9500,	2100,	5200,	6800,	3700
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2750	,380	,2620	,3770,	1560
                                },
                                {  //$50-100/month
                                        5000	,700	,4320	,5940	,2600
                                },
                                {  //$100-150/month
                                        5500,	1350,	5800	,6200	,4310
                                },
                                {  //$150-200/month
                                        6300,	1900,	5500	,4280,	3800
                                },
                                {  //>$200/month
                                        10100	,2780	,5500	,6600,	4640
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3010,	560	,3000	,3940,	4070
                                },
                                {  //$50-100/month
                                        3840,	1380,	4900,	6330,	3930
                                },
                                {  //$100-150/month
                                        6200,	2000,	5690,	4110,	4500
                                },
                                {  //$150-200/month
                                        8300,	2500,	5600,	4500,	4640
                                },
                                {  //>$200/month
                                        10100,	3000,	5800,	6900,	4700
                                }}},
                {  //3-4 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2500,	500,	2600,	2100,	2500
                                },
                                {  //$50-100/month
                                        3000	,580	,3250,	3400,	3300
                                },
                                {  //$100-150/month
                                        3500	,1320	,3900	,4100	,3520
                                },
                                {  //$150-200/month
                                        6200,	1900,	4800,	5200,	3720
                                },
                                {  //>$200/month
                                        10200,	2300	,5300	,7200,	4000
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3111,	500,	2730	,4670	,1900
                                },
                                {  //$50-100/month
                                        6500	,950,	4800	,6140	,3300
                                },
                                {  //$100-150/month
                                        6800	,1520,	6200	,6420	,4600
                                },
                                {  //$150-200/month
                                        8500	,2150,	5720,	5180	,4220
                                },
                                {  //>$200/month
                                        11200	,3000,	5800,	6800,	5000
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3300	,890	,3468,	4840	,5000
                                },
                                {  //$50-100/month
                                        3900	,1600	,5320	,6440,	4360
                                },
                                {  //$100-150/month
                                        7000,	2200,	6250,	5010	,4780
                                },
                                {  //$150-200/month
                                        9000	,2650,	5800	,5380,	5000
                                },
                                {  //>$200/month
                                        10300,	3800	,6100,	7500,	5100
                                }}},
                {  //5 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2800	,550,	2720	,2500,	2600
                                },
                                {  //$50-100/month
                                        3200	,600	,3500	,3800	,3400
                                },
                                {  //$100-150/month
                                        3800	,1420,	4050,	5000	,3800
                                },
                                {  //$150-200/month
                                        8000	,2000	,5100	,5800,	4000
                                },
                                {  //>$200/month
                                        12000	,3000,	5600	,7800	,4300
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3580,	590	,2800,	5570	,2200
                                },
                                {  //$50-100/month
                                        7300,	1100,	5500,	6340,	3800
                                },
                                {  //$100-150/month
                                        8340,	1700,	6100,	6500	,5100
                                },
                                {  //$150-200/month
                                        9000,	2400	,5900,	6080	,4400
                                },
                                {  //>$200/month
                                        14000,3500,	6200	,7400,	5430
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3600	,1000	,3760	,5740,	5600
                                },
                                {  //$50-100/month
                                        5100,	1750,	5800,	6900,	5000
                                },
                                {  //$100-150/month
                                        8000,	2300,	6500	,5910,	5360
                                },
                                {  //$150-200/month
                                        9500,	2800	,6000,	6200	,5400
                                },
                                {  //>$200/month
                                        11000	,4300	,6350	,7850	,5500
                                }}}},
        {  //condo
                {  //1 person
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        1680	,200	,0	,1320,	1600
                                },
                                {  //$50-100/month
                                        2240,	500,	0,	2100	,1800
                                },
                                {  //$100-150/month
                                        2800,	900	,0,	3000	,2800
                                },
                                {  //$150-200/month
                                        3700	,1400,	0	,3300	,3000
                                },
                                {  //>$200/month
                                        5000,	1900,	0	,5700	,3500
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2060	,300,	0	,1500	,1800
                                },
                                {  //$50-100/month
                                        2500,	550	,0,	3000	,2200
                                },
                                {  //$100-150/month
                                        3100	,1250,	0	,4100	,3200
                                },
                                {  //$150-200/month
                                        4000	,1900	,0,	4550	,3100
                                },
                                {  //>$200/month
                                        5350,	2300	,0,	6000,	3900
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        2440,	350	,0,	1800,	2300
                                },
                                {  //$50-100/month
                                        2700	,610,	0	,3650	,2600
                                },
                                {  //$100-150/month
                                        3670,	1500,	0	,4500,	3500
                                },
                                {  //$150-200/month
                                        4250	,2150,	0	,5000	,3530
                                },
                                {  //>$200/month
                                        6000,	2600	,0	,6500,	4100
                                }}},
                {  //2 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        1870	,250	,0	,1550	,1850
                                },
                                {  //$50-100/month
                                        2430	,550	,0	,2400	,2000
                                },
                                {  //$100-150/month
                                        3000,	1100	,0	,3300	,3000
                                },
                                {  //$150-200/month
                                        4100	,1600,	0	,3700	,3100
                                },
                                {  //>$200/month
                                        7200,	2100,	0	,6000	,3600
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2260	,400	,0,	1700,	2200
                                },
                                {  //$50-100/month
                                        2880	,670	,0	,3400,	2500
                                },
                                {  //$100-150/month
                                        3300	,1450	,0	,4600	,3500
                                },
                                {  //$150-200/month
                                        4700,	2300,	0,	4950,	3300
                                },
                                {  //>$200/month
                                        7550	,2500,	0	,6300	,4200
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        2727	,570	,0	,2100,	2600
                                },
                                {  //$50-100/month
                                        3100	,690	,0,	4050	,2900
                                },
                                {  //$100-150/month
                                        3870	,1700,	0,	5000	,3700
                                },
                                {  //$150-200/month
                                        5050	,2550,	0	,5300	,3730
                                },
                                {  //>$200/month
                                        7800,	3100,	0	,6800	,4400
                                }}},
                {  //3-4 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2170,	380,	0	,1800	,2000
                                },
                                {  //$50-100/month
                                        2719,	580	,0,	2800,	2300
                                },
                                {  //$100-150/month
                                        3200	,1200	,0,	3700,	3300
                                },
                                {  //$150-200/month
                                        4600,	1700	,0	,4400,	3500
                                },
                                {  //>$200/month
                                        8000,	2200	,0	,6600	,3900
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        2540	,500,	0,	2100,	2500
                                },
                                {  //$50-100/month
                                        3110,	780	,0,	3800,	2900
                                },
                                {  //$100-150/month
                                        3500,	1750	,0,	5000,	3600
                                },
                                {  //$150-200/month
                                        5200,	2700,	0	,5350,	3700
                                },
                                {  //>$200/month
                                        8200	,2700,	0	,7000,	4500
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3010,	900	,0	,2450,	2900
                                },
                                {  //$50-100/month
                                        3300	,820,	0,	4650	,3300
                                },
                                {  //$100-150/month
                                        4100	,2000,	0	,5400,	4200
                                },
                                {  //$150-200/month
                                        5400,	2850,	0	,5600,	4200
                                },
                                {  //>$200/month
                                        8500,	3600,	0	,7400,	4900
                                }}},
                {  //5 people
                        {  //<1000 sqft
                                {  //<$50/month bill
                                        2440,	450	,0,	2000,	2100
                                },
                                {  //$50-100/month
                                        2997,	600	,0,	3200,	2400
                                },
                                {  //$100-150/month
                                        3500	,1300,	0,	4300,	3500
                                },
                                {  //$150-200/month
                                        5000,	1900,	0	,5200	,3900
                                },
                                {  //>$200/month
                                        10000,	2600,	0	,7000,	4200
                                }},
                        {  //1-2000 sqft
                                {  //<$50/month bill
                                        3010,	620,	0,	2300	,2700
                                },
                                {  //$50-100/month
                                        3320	,900	,0	,4200	,3300
                                },
                                {  //$100-150/month
                                        3900	,2100	,0,	5400,	4000
                                },
                                {  //$150-200/month
                                        5900,	3000,	0,	5650,	4100
                                },
                                {  //>$200/month
                                        10500,	3100,	0,	7400	,4700
                                }},
                        {  //>2000 sqft
                                {  //<$50/month bill
                                        3577	,980,	0	,2600,	3300
                                },
                                {  //$50-100/month
                                        3600	,980,	0	,5150	,3600
                                },
                                {  //$100-150/month
                                        4300	,2350,	0	,5700,	4600
                                },
                                {  //$150-200/month
                                        6200	,3150	,0	,6000,	4630
                                },
                                {  //>$200/month
                                        11100	,4000	,0	,7800,	5100
                                }}}}};

};