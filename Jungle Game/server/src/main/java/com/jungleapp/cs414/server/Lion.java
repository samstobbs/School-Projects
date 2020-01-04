package com.jungleapp.cs414.server;

import java.util.ArrayList;

public class Lion extends BigCat {

    Lion(JungleBoard board, String color){
        super(board, color);
        rank = 7;
        super.legalMoves = legalMoves();
        name = "lion";
    }

    Lion(Piece piece) {
        super(piece);
    }
    //The lion uses the legal moves method in BigCat
    public ArrayList<String> legalMoves() {
        return super.legalMoves();
    }

}
