package kookyinfomedia.com.gtcg;

import static kookyinfomedia.com.gtcg.GamePlay.player;

/*for controlling game logic (Checks the winner and changes the scores.)*/


class Controller {

    private int playerNum,score=0;

    int checkWin(ModelClass m1, ModelClass m2, int betField){
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
                if((!(m1.getCoastline().trim().equals("Landlocked"))) && (!(m2.getCoastline().trim().equals("Landlocked")))) {
                    if (Integer.parseInt(m1.getCoastline()) < Integer.parseInt(m2.getCoastline()))
                        playerNum = first;
                    else if (Integer.parseInt(m1.getCoastline()) > Integer.parseInt(m2.getCoastline()))
                        playerNum = second;
                    else
                        playerNum = first;
                }
                else if((m1.getCoastline().trim().equals("Landlocked")) && ((!(m2.getCoastline().trim().equals("Landlocked")))))
                        playerNum= second;
                else if((!(m1.getCoastline().trim().equals("Landlocked"))) && (m2.getCoastline().trim().equals("Landlocked")))
                        playerNum= first;
                else if((m1.getCoastline().trim().equals("Landlocked")) && (m2.getCoastline().trim().equals("Landlocked")))
                    playerNum= first;
                break;
                }

            case 4:{
                if(Integer.parseInt(m1.getaUnits())<Integer.parseInt(m2.getaUnits()))
                    playerNum= second;
                else if(Integer.parseInt(m1.getaUnits())>Integer.parseInt(m2.getaUnits()))
                    playerNum= first;
                else
                    playerNum= first;
                break;
            }
            case 5:{
                if(Integer.parseInt(m1.getbCountries())<Integer.parseInt(m2.getbCountries()))
                    playerNum= second;
                else if(Integer.parseInt(m1.getbCountries())>Integer.parseInt(m2.getbCountries()))
                    playerNum= first;
                else
                    playerNum= first;
                break;
            }
            case 6:{
                if(Integer.parseInt(m1.gethPoint())<Integer.parseInt(m2.gethPoint()))
                    playerNum= second;
                else if(Integer.parseInt(m1.gethPoint())>Integer.parseInt(m2.gethPoint()))
                    playerNum= first;
                else
                    playerNum= first;
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

    int betDecisionComputer(ModelClass m2){
        int betField,area,population,coastline,aUnits,bCountries,hPoint;
        area=Integer.parseInt(m2.getArea());
        population=Integer.parseInt(m2.getPopulation());
        if(!(m2.getCoastline().equals("Landlocked")))
            coastline=Integer.parseInt(m2.getCoastline());
        else
            coastline=5000;
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
                    betField=2;
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
                    betField=3;
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
