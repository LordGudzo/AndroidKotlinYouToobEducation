package com.firstproject.myclicker

data class ClickerModel(var point: Int)

class ClickerRepository {
    private var _pointModel = ClickerModel(0)

    fun getPoint() = _pointModel

    fun plusPoint() {
        _pointModel.point++
    }

    fun minusPoint() {
        _pointModel.point--
    }
}