package com.codecool.enterprise.overcomplicated.model;

public class TictactoeGame {

    private String gameFields = "---------";

    private boolean gameClosed = false;

    public String getGameFields() {
        return gameFields;
    }

    public boolean isGameClosed() {
        return gameClosed;
    }
    
    public void setAGameField(int index, String playerSign) {
        gameFields = gameFields.substring(0, index) +
                    playerSign +
                    gameFields.substring(index+1, gameFields.length());
    }

    public boolean checkFieldIsEmpty(int index) {
        if (gameFields.substring(index, index + 1).equals("-")){
            return false;
        }
        return true;
    }

    public boolean playerWins() {
        return checkWin("O");
    }

    public boolean computerWins() {
        return checkWin("X");
    }

    private boolean checkWin(String sign){
        boolean winning = false;
        String winningRow = sign + sign + sign;
        if (gameFields.substring(0, 3).equals(winningRow)){
            winning = true;
        } else if (gameFields.substring(3, 6).equals(winningRow)){
            winning = true;
        } else if (gameFields.substring(6, 9).equals(winningRow)){
            winning = true;
        } else if (gameFields.substring(0, 1).equals(sign) &&
                gameFields.substring(3, 4).equals(sign) &&
                gameFields.substring(6, 7).equals(sign)){
            winning = true;
        } else if (gameFields.substring(1, 2).equals(sign) &&
                gameFields.substring(4, 5).equals(sign) &&
                gameFields.substring(7, 8).equals(sign)){
            winning = true;
        } else if (gameFields.substring(2, 3).equals(sign) &&
                gameFields.substring(5, 6).equals(sign) &&
                gameFields.substring(8, 9).equals(sign)){
            winning = true;
        } else if (gameFields.substring(0, 1).equals(sign) &&
                gameFields.substring(4, 5).equals(sign) &&
                gameFields.substring(8, 9).equals(sign)){
            winning = true;
        } else if (gameFields.substring(2, 3).equals(sign) &&
                gameFields.substring(4, 5).equals(sign) &&
                gameFields.substring(6, 7).equals(sign)){
            winning = true;
        }

        if (winning){
            gameClosed = true;
        }

        return winning;
    }

    public boolean draw() {
        if (gameFields.contains("-")){
            return false;
        }

        gameClosed = true;
        return true;
    }
}
