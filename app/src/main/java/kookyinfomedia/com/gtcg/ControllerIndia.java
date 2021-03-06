package kookyinfomedia.com.gtcg;

import java.util.Random;

import static kookyinfomedia.com.gtcg.GamePlayIndia.player;

// for controlling game logic (Checks the winner and changes the scores.)


public class ControllerIndia {

    public int playerNum,score=0;
    int first,second,temp;
    Random ran=new Random();
    String riverName,cropName,mineralName;
    //andy get river,crop,mineral by poistion+1
    public int checkWin(ModelClassIndia m1, ModelClassIndia m2, int betField, String riverName, String cropName, String mineralName, String[] arrRivers, String[] arrCrops, String[] arrMinerals){
        first=player;
        if(first==1)
            second=2;
        else
            second=1;

        switch(betField){
            case 1:{
                if((Integer.parseInt(m1.getArea()))<(Integer.parseInt(m2.getArea())))
                    playerNum=first;
                else if((Integer.parseInt(m1.getArea()))>(Integer.parseInt(m2.getArea())))
                    playerNum=second;
                else
                    playerNum=first;
                break;
            }
            case 2:{
                if(Integer.parseInt(m1.getPopulation())<Integer.parseInt(m2.getPopulation()))
                    playerNum=first;
                else if(Integer.parseInt(m1.getPopulation())>Integer.parseInt(m2.getPopulation()))
                    playerNum=second;
                else
                    playerNum=first;
                break;
            }
            case 3:{
                if(Integer.parseInt(m1.getDistricts())<Integer.parseInt(m2.getDistricts()))
                    playerNum=second;
                else if(Integer.parseInt(m1.getDistricts())>Integer.parseInt(m2.getDistricts()))
                    playerNum=first;
                else
                    playerNum=first;
                break;
                }

            case 4:{
                if(Integer.parseInt(m1.getNational_parks())<Integer.parseInt(m2.getNational_parks()))
                    playerNum=second;
                else if(Integer.parseInt(m1.getNational_parks())>Integer.parseInt(m2.getNational_parks()))
                    playerNum=first;
                else
                    playerNum=first;
                break;
            }
            case 5:{
                    if(riverName.equalsIgnoreCase("No River")){
                        playerNum=second;
                    }
                    else {
                        for (int i = 0; i < arrRivers.length; i++) {
                            if (riverName.equalsIgnoreCase(arrRivers[i])) {

                                playerNum = second;
                                break;
                            } else
                                playerNum = first;
                        }
                    }
                break;
            }
            case 6:{
                if(cropName.equalsIgnoreCase("No Crops")){
                    playerNum=second;
                }
                else {
                    for (int i = 0; i < arrCrops.length; i++) {
                        if (cropName.equalsIgnoreCase(arrCrops[i])) {
                            playerNum = second;
                            break;
                        } else
                            playerNum = first;
                    }
                }
                break;

            }
            case 7:{
                if(mineralName.equalsIgnoreCase("No Minerals")){
                    playerNum=second;
                }
                else {
                    for (int i = 0; i < arrMinerals.length; i++) {
                        if (mineralName.equalsIgnoreCase(arrMinerals[i])) {
                            playerNum = second;
                            break;
                        } else
                            playerNum = first;
                    }
                }
                break;
            }

        }
        return playerNum;
    }
    public int updateScore(int playerNum){
        if(playerNum==1)
            score=1;
        else if(playerNum==2)
            score=0;
        return score;
    }

    public int betDecisionComputer(ModelClassIndia m2){
        int betField,area,population,districts,national_park,crop,river,mineral;
        area=Integer.parseInt(m2.getArea());
        population=Integer.parseInt(m2.getPopulation());
        districts=Integer.parseInt(m2.getDistricts());
        national_park=Integer.parseInt(m2.getNational_parks());

        if((area<population && districts<10 && national_park<4)||(area<3)){
            betField=1;
        }
        else if((population<area && districts<10 && national_park<4)||(population<3)){
            betField=2;
        }
        else if(districts>10 && national_park<4){
            betField=3;
        }
        else
            betField=4;
        switch(betField){
            case 1:{
                temp=-1;
                if(area<=10)
                    betField=1;
                else
                    temp = ran.nextInt(3);
                if(temp!=-1){
                    if(temp==0)
                        betField=5;
                    else if(temp==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
            case 2:{
                temp=-1;
                if(population<=10)
                    betField=2;
                else
                    temp = ran.nextInt(3);
                if(temp!=-1){
                    if(temp==0)
                        betField=5;
                    else if(temp==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
            case 3:{
                temp=-1;
                if(districts>=10)
                    betField=3;
                else
                    temp = ran.nextInt(3);
                if(temp!=-1){
                    if(temp==0)
                        betField=5;
                    else if(temp==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
            case 4:{
                temp=-1;
                if(national_park>=5)
                    betField=4;
                else
                    temp = ran.nextInt(3);
                if(temp!=-1){
                    if(temp==0)
                        betField=5;
                    else if(temp==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
        }
        return betField;
    }

}
