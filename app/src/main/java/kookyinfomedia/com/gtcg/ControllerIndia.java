package kookyinfomedia.com.gtcg;

import java.util.Random;

import static kookyinfomedia.com.gtcg.GamePlay.player;

/********************************* for controlling game logic (Checks the winner and changes the scores.) ****************************/


public class ControllerIndia {

    public int playerNum,score=0;
    int first,second,temp;
    Random ran=new Random();
    String riverName,cropName,mineralName;
    /////andy get river,crop,mineral by poistion+1
    public int checkWin(ModelClassIndia m1,ModelClassIndia m2,int betField,int river,int crop,int mineral){
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
                    playerNum=first;
                else if(Integer.parseInt(m1.getDistricts())>Integer.parseInt(m2.getDistricts()))
                    playerNum=second;
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
                switch(river){
                    case 1: {
                        riverName = m1.getRiver1();
                        break;
                    }
                    case 2: {
                        riverName = m1.getRiver2();
                        break;
                    }
                    case 3: {
                        riverName = m1.getRiver3();
                        break;
                    }
                    case 4: {
                        riverName = m1.getRiver4();
                        break;
                    }
                }
               if(riverName.equalsIgnoreCase(m2.getRiver1()))
                   playerNum=first;
                else if(riverName.equalsIgnoreCase(m2.getRiver2()))
                   playerNum=first;
               else if(riverName.equalsIgnoreCase(m2.getRiver3()))
                   playerNum=first;
               else if(riverName.equalsIgnoreCase(m2.getRiver4()))
                   playerNum=first;
                else
                    playerNum=second;
                break;

            }
            case 6:{
                switch(crop){
                    case 1: {
                        cropName = m1.getCrop1();
                        break;
                    }
                    case 2: {
                        cropName = m1.getCrop2();
                        break;
                    }
                    case 3: {
                        cropName = m1.getCrop3();
                        break;
                    }
                    case 4: {
                        cropName = m1.getCrop4();
                        break;
                    }
                    case 5: {
                        cropName = m1.getCrop5();
                        break;
                    }
                    case 6: {
                        cropName = m1.getCrop6();
                        break;
                    }
                    case 7: {
                        cropName = m1.getCrop7();
                        break;
                    }
                    case 8: {
                        cropName = m1.getCrop8();
                        break;
                    }
                    case 9: {
                        cropName = m1.getCrop9();
                        break;
                    }
                    case 10: {
                        cropName = m1.getCrop10();
                        break;
                    }
                    case 11: {
                        cropName = m1.getCrop11();
                        break;
                    }

                }
                if(cropName.equalsIgnoreCase(m2.getCrop1()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop2()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop3()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop4()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop5()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop6()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop7()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop8()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop9()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop10()))
                    playerNum=first;
                else if(cropName.equalsIgnoreCase(m2.getCrop11()))
                    playerNum=first;
                else
                    playerNum=second;
                break;
            }
            case 7:{
                switch(mineral) {
                    case 1: {
                        mineralName = m1.getMineral1();
                        break;
                    }
                    case 2: {
                        mineralName = m1.getMineral2();
                        break;
                    }
                    case 3: {
                        mineralName = m1.getMineral3();
                        break;
                    }
                    case 4: {
                        mineralName = m1.getMineral4();
                        break;
                    }
                    case 5: {
                        mineralName = m1.getMineral5();
                        break;
                    }
                    case 6: {
                        mineralName = m1.getMineral6();
                        break;
                    }
                    case 7: {
                        mineralName = m1.getMineral7();
                        break;
                    }
                    case 8: {
                        mineralName = m1.getMineral8();
                        break;
                    }
                    case 9: {
                        mineralName = m1.getMineral9();
                        break;
                    }
                    case 10: {
                        mineralName = m1.getMineral10();
                        break;
                    }
                    case 11: {
                        mineralName = m1.getMineral11();
                        break;
                    }
                }
                if(mineralName.equalsIgnoreCase(m2.getMineral1()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral2()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral3()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral4()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral5()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral6()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral7()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral8()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral9()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral10()))
                    playerNum=first;
                else if(mineralName.equalsIgnoreCase(m2.getMineral11()))
                    playerNum=first;
                else
                    playerNum=second;
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
                if(districts<=10)
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
                if(national_park<=10)
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
