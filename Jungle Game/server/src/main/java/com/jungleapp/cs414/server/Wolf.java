package com.jungleapp.cs414.server;

import java.util.ArrayList;

public class Wolf extends Piece {

    Wolf(JungleBoard board, String color){
        super(board, color);
        rank = 3;
        super.legalMoves = legalMoves();
        name = "wolf";
    }

    Wolf(Piece piece) {
        super(piece);
    }

    //The wolf uses regular legal moves
    public ArrayList<String> legalMoves() {
        return super.legalMoves();
    }
}
