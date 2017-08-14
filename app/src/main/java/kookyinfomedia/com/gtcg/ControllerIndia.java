package kookyinfomedia.com.gtcg;

import java.util.Random;

import static kookyinfomedia.com.gtcg.GamePlay.player;

/*for controlling game logic (Checks the winner and changes the scores.)*/


class ControllerIndia {

    private int playerNum,score=0;
    private Random ran=new Random();

    //andy get river,crop,mineral by position+1
    int checkWin(ModelClassIndia m1, ModelClassIndia m2, int betField, String riverName, String cropName, String mineralName, String[] arrRivers, String[] arrCrops, String[] arrMinerals){
        int first = player;
        int second;
        if(first ==1)
            second =2;
        else
            second =1;

        switch(betField){
            case 1:{
                if((Integer.parseInt(m1.getArea()))<(Integer.parseInt(m2.getArea())))
                    playerNum= first;
                else if((Integer.parseInt(m1.getArea()))>(Integer.parseInt(m2.getArea())))
                    playerNum= second;
                else
                    playerNum= first;
                break;
            }
            case 2:{
                if(Integer.parseInt(m1.getPopulation())<Integer.parseInt(m2.getPopulation()))
                    playerNum= first;
                else if(Integer.parseInt(m1.getPopulation())>Integer.parseInt(m2.getPopulation()))
                    playerNum= second;
                else
                    playerNum= first;
                break;
            }
            case 3:{
                if(Integer.parseInt(m1.getDistricts())<Integer.parseInt(m2.getDistricts()))
                    playerNum= second;
                else if(Integer.parseInt(m1.getDistricts())>Integer.parseInt(m2.getDistricts()))
                    playerNum= first;
                else
                    playerNum= first;
                break;
                }

            case 4:{
                if(Integer.parseInt(m1.getNational_parks())<Integer.parseInt(m2.getNational_parks()))
                    playerNum= second;
                else if(Integer.parseInt(m1.getNational_parks())>Integer.parseInt(m2.getNational_parks()))
                    playerNum= first;
                else
                    playerNum= first;
                break;
            }
            case 5:{
                for (String arrRiver : arrRivers) {
                    if (riverName.equalsIgnoreCase(arrRiver)) {
                        playerNum = first;
                        break;
                    } else
                        playerNum = second;
                }
                break;
            }
            case 6:{
                for (String arrCrop : arrCrops) {
                    if (cropName.equalsIgnoreCase(arrCrop)) {
                        playerNum = first;
                        break;
                    } else
                        playerNum = second;
                }
                break;

            }
            case 7:{
                for (String arrMineral : arrMinerals) {
                    if (mineralName.equalsIgnoreCase(arrMineral)) {
                        playerNum = first;
                        break;
                    } else
                        playerNum = second;
                }
                break;
            }

        }
        return playerNum;
    }
    int updateScore(int playerNum){
        if(playerNum==1)
            score=1;
        else if(playerNum==2)
            score=0;
        return score;
    }

    int betDecisionComputer(ModelClassIndia m2){
        int betField,area,population,districts,national_park;
        area=Integer.parseInt(m2.getArea());
        population=Integer.parseInt(m2.getPopulation());
        districts=Integer.parseInt(m2.getDistricts());
        national_park=Integer.parseInt(m2.getNational_parks());

        if(area<population && area<districts && area<national_park){
            betField=1;
        }
        else if(population<area && population<districts && population<national_park){
            betField=2;
        }
        else if(districts<area && districts<population && districts<national_park){
            betField=3;
        }
        else
            betField=4;
        switch(betField){
            case 1:
                int temp;
            {
                temp =-1;
                if(area<=10)
                    betField=1;
                else
                    temp = ran.nextInt(3);
                if(temp !=-1){
                    if(temp ==0)
                        betField=5;
                    else if(temp ==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
            case 2:{
                temp =-1;
                if(population<=10)
                    betField=2;
                else
                    temp = ran.nextInt(3);
                if(temp !=-1){
                    if(temp ==0)
                        betField=5;
                    else if(temp ==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
            case 3:{
                temp =-1;
                if(districts<=10)
                    betField=3;
                else
                    temp = ran.nextInt(3);
                if(temp !=-1){
                    if(temp ==0)
                        betField=5;
                    else if(temp ==1)
                        betField=6;
                    else
                        betField=7;
                }
                break;
            }
            case 4:{
                temp =-1;
                if(national_park<=10)
                    betField=4;
                else
                    temp = ran.nextInt(3);
                if(temp !=-1){
                    if(temp ==0)
                        betField=5;
                    else if(temp ==1)
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
