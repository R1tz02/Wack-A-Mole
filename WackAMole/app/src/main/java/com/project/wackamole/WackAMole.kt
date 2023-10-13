package com.project.wackamole
import kotlin.random.Random
const val BOARD_SIZE = 3

class WackAMole {


    //Private variable and methods
    private var board = Array(BOARD_SIZE) { BooleanArray(BOARD_SIZE) {false} }
    private var score = 0



    private fun incrementScore(){
        score++
    }


    //Public methods
    fun newGame(){
        score = 0
        //https://stackoverflow.com/questions/59315080/random-number-generator-in-kotlin

        val row = Random.nextInt(2)
        val col = Random.nextInt(2)
        board[row][col] = true
    }

    fun getScore() : Int {
        return score
    }
    fun molePresent(row:Int, col:Int) : Boolean {
        return board[row][col]
    }

    fun gameOver(){
        for(row in 0 until BOARD_SIZE){
            for(col in 0 until BOARD_SIZE) {
                board[row][col] = false
            }
        }
    }

    fun wackAMole(row:Int, col:Int)
    {
        if(molePresent(row, col)) {
            incrementScore()
            board[row][col] = false
            var newRow: Int
            var newCol: Int
            do {
                newRow = Random.nextInt(3)
                newCol = Random.nextInt(3)
            }while(newRow == row && newCol == col)

            
            board[newRow][newCol] = true
        }
    }




    //State Preservation
    var state: BooleanArray
        get(){
            val stateArray = booleanArrayOf()
            var index = 0
            for(row in 0 until BOARD_SIZE){
                for(col in 0 until BOARD_SIZE){
                    stateArray[index] = board[row][col]
                    index++
                }
            }
            return stateArray
        }

        set(value){
            var index = 0
            for(row in 0 until BOARD_SIZE){
                for(col in 0 until BOARD_SIZE) {
                    board[row][col] = value[index]
                    index++
                }
            }
        }

}