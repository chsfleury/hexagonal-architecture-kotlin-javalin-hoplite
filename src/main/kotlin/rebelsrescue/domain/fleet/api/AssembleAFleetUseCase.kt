package rebelsrescue.domain.fleet.api

import rebelsrescue.domain.fleet.Fleet

interface AssembleAFleetUseCase: (Int) -> Fleet {

    override fun invoke(numberOfPassengers: Int): Fleet = forPassengers(numberOfPassengers)
    
    fun forPassengers(numberOfPassengers: Int): Fleet

}