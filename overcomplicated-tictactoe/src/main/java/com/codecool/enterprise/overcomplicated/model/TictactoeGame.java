package com.codecool.enterprise.overcomplicated.model;

public class TictactoeGame {

    private String gameFields = "---------";

    public String getGameFields() {
        return gameFields;
    }

    public void setGameFields(String gameFields) {
        this.gameFields = gameFields;
    }

    public String getAGameField(int index) {
        return gameFields.substring(index, index+1);
    }

    public void setAGameField(int index, String playerSign) {
        gameFields = gameFields.substring(0, index) +
                    playerSign +
                    gameFields.substring(index+1, gameFields.length());
    }


}
