package com.example.wheatherapp


data class Forecast(
    var weather:List<Weather>,
    var main:Main?=null,
    var sys:Sys?=null,
    var name:String?=null,
    var dt:Long=0

)

data class Weather(
    var main:String?=null,
    var description:String?=null,
    var icon:String?=null
)
data class Main(
    var temp:Double?=0.0,
    var pressure:Int?=0
)
data class Sys(
    var sunrise:Long=0,
    var sunset:Long=0,
    var country:String?=null
)


