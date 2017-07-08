package kookyinfomedia.com.gtcg;

import java.util.Random;

/********************************* for controlling game logic (Checks the winner and changes the scores. ****************************/


public class Controller {

    public int playerNum=0,score=0;
    public int checkWin(ModelClass m1,ModelClass m2,int betField){
        switch(betField){

            case 1:{
                if((Integer.parseInt(m1.getArea()))<(Integer.parseInt(m2.getArea())))
                    playerNum=1;
                else if((Integer.parseInt(m1.getArea()))>(Integer.parseInt(m2.getArea())))
                    playerNum=2;
                else
                    playerNum=1;
                break;
            }
            case 2:{
                if(Integer.parseInt(m1.getPopulation())<Integer.parseInt(m2.getPopulation()))
                    playerNum=1;
                else if(Integer.parseInt(m1.getPopulation())>Integer.parseInt(m2.getPopulation()))
                    playerNum=2;
                else
                    playerNum=1;
                break;
            }
            case 3:{
                if(!(m1.getCoastline().trim().equals("LANDLOCKED")) && !(m2.getCoastline().trim().equals("LANDLOCKED"))) {
                    if (Integer.parseInt(m1.getCoastline()) < Integer.parseInt(m2.getCoastline()))
                        playerNum = 1;
                    else if (Integer.parseInt(m1.getCoastline()) > Integer.parseInt(m2.getCoastline()))
                        playerNum = 2;
                    else
                        playerNum = 1;
                }
                else if((m1.getCoastline().trim().equals("LANDLOCKED")) && !(m2.getCoastline().trim().equals("LANDLOCKED")))
                        playerNum=2;
                else if(!(m1.getCoastline().trim().equals("LANDLOCKED")) && (m2.getCoastline().trim().equals("LANDLOCKED")))
                        playerNum=1;
                else if((m1.getCoastline().trim().equals("LANDLOCKED")) && (m2.getCoastline().trim().equals("LANDLOCKED")))
                    playerNum=1;
                break;
                }

            case 4:{
                if(Integer.parseInt(m1.getaUnits())<Integer.parseInt(m2.getaUnits()))
                    playerNum=2;
                else if(Integer.parseInt(m1.getaUnits())>Integer.parseInt(m2.getaUnits()))
                    playerNum=1;
                else
                    playerNum=1;
                break;
            }
            case 5:{
                if(Integer.parseInt(m1.getbCountries())<Integer.parseInt(m2.getbCountries()))
                    playerNum=2;
                else if(Integer.parseInt(m1.getbCountries())>Integer.parseInt(m2.getbCountries()))
                    playerNum=1;
                else
                    playerNum=1;
                break;
            }
            case 6:{
                if(Integer.parseInt(m1.gethPoint())<Integer.parseInt(m2.gethPoint()))
                    playerNum=2;
                else if(Integer.parseInt(m1.gethPoint())>Integer.parseInt(m2.gethPoint()))
                    playerNum=1;
                else
                    playerNum=1;
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

    public int betDecisionComputer(ModelClass m2){
        Random ran= new Random();
        int betField,area,population,coastline,aUnits,bCountries,hPoint;
        area=Integer.parseInt(m2.getArea());
        population=Integer.parseInt(m2.getPopulation());
        if(!(m2.getCoastline().equals("LANDLOCKED")))
            coastline=Integer.parseInt(m2.getCoastline());
        else
            coastline=2000;
        aUnits=Integer.parseInt(m2.getaUnits());
        bCountries=Integer.parseInt(m2.getbCountries());
        hPoint=Integer.parseInt(m2.gethPoint());

        if(area<population && area<coastline){
            betField=1;
        }
        else if(population<area && population<coastline){
            betField=2;
        }
        else
            betField=3;
        switch(betField){
            case 1:{
                if(area<=5)
                    betField=1;
                else if(hPoint>2500)
                    betField=6;
                else if(aUnits>15)
                    betField=4;
                else if(bCountries>10)
                    betField=5;
                //else
                    //betField = ran.nextInt(6);
                break;
            }
            case 2:{
                if(population<=5)
                    betField=1;
                else if(hPoint>2500)
                    betField=6;
                else if(aUnits>15)
                    betField=4;
                else if(bCountries>10)
                    betField=5;
                //else
                    //betField = ran.nextInt(6);
                break;
            }
            case 3:{
                if(coastline<=5)
                    betField=1;
                else if(hPoint>2500)
                    betField=6;
                else if(aUnits>15)
                    betField=4;
                else if(bCountries>10)
                    betField=5;
                //else
                    //betField = ran.nextInt(6);
                break;
            }
           // default:
              //  betField = ran.nextInt(5);
        }
        return betField;
    }

}
