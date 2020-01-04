package com.jungleapp.cs414.server;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RatTest {

    @Test
    public void testConstructor() {
        JungleBoard board = new JungleBoard();
        Rat ratTest = new Rat(board, "RED");
        assertEquals(1, ratTest.getRank());
        assertEquals("RED", ratTest.getColor());
    }

    @Test
    public void testInitialMoves() {
        JungleBoard board = new JungleBoard();
        board.initialize();

        try {
            assertTrue(board.getPiece(2, 0).legalMoves.containsAll(Arrays.asList("10","30","21")));
            assertTrue(board.getPiece(6, 6).legalMoves.containsAll(Arrays.asList("56","76","65")));
        } catch (IllegalPositionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void legalMoves() {
        JungleBoard board = new JungleBoard();

        board.placePiece(new Rat(board,"RED"),2, 2);
        board.placePiece(new Rat(board,"RED"),2, 3);
        board.placePiece(new Rat(board,"RED"),1, 2);

        try {
            assertTrue(board.getPiece(2, 2).legalMoves.containsAll(Arrays.asList("32","21")));
        } catch (IllegalPositionException e) {
            e.printStackTrace();
        }
    }
}