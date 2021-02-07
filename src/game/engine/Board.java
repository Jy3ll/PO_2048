package game.engine;


import java.util.*;

public class Board{
    private int [][] elements;
    private int score;
    private boolean winCondition;
    private boolean lossCondition;
    private int freeSpace;
    private int highestPower;
    public Board(){
        this.elements=new int[4][4];
        this.score=0;
        this.winCondition=false;
        this.lossCondition=false;
        this.freeSpace=16;
        this.highestPower=1;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                this.elements[i][j]=0;
            }
        }
        this.placeInRandom();
    }


    public void moveInDirection(MoveDirection direction){
        boolean stateChanged=false;
        switch(direction){
            case LEFT:
                for(int i=0;i<4;i++){
                    int limit=-1;
                    for(int j=0;j<4;j++){
                        if(this.elements[i][j]==0 && limit==-1){
                            limit=j;
                        }
                        if(this.elements[i][j]!=0 && limit!=-1){
                            this.elements[i][limit]=this.elements[i][j];
                            this.elements[i][j]=0;
                            j=limit;
                            limit=-1;
                            stateChanged=true;
                        }
                    }
                }
                break;
            case RIGHT:
                for(int i=0;i<4;i++){
                    int limit=-1;
                    for(int j=3;j>=0;j--){
                        if(this.elements[i][j]==0 && limit==-1){
                            limit=j;
                        }
                        if(this.elements[i][j]!=0 && limit!=-1){
                            this.elements[i][limit]=this.elements[i][j];
                            this.elements[i][j]=0;
                            j=limit;
                            limit=-1;
                            stateChanged=true;
                        }
                    }
                }
                break;
            case UP:
                for(int j=0;j<4;j++){
                    int limit=-1;
                    for(int i=0;i<4;i++){
                        if(this.elements[i][j]==0 && limit==-1){
                            limit=i;
                        }
                        if(this.elements[i][j]!=0 && limit!=-1){
                            this.elements[limit][j]=this.elements[i][j];
                            this.elements[i][j]=0;
                            i=limit;
                            limit=-1;
                            stateChanged=true;
                        }
                    }
                }
                break;
            case DOWN:
                for(int j=0;j<4;j++){
                    int limit=-1;
                    for(int i=3;i>=0;i--){
                        if(this.elements[i][j]==0 && limit==-1){
                            limit=i;
                        }
                        if(this.elements[i][j]!=0 && limit!=-1){
                            this.elements[limit][j]=this.elements[i][j];
                            this.elements[i][j]=0;
                            i=limit;
                            limit=-1;
                            stateChanged=true;
                        }
                    }
                }
                break;

        }
        stateChanged=(merge(direction) || stateChanged);
        if(stateChanged){
            this.placeInRandom();
        }
    }

    private boolean merge(MoveDirection direction){
        boolean stateChanged=false;
        switch(direction){
            case LEFT:
                for(int i=0;i<4;i++){
                    for(int j=0;j<3 && this.elements[i][j+1]!=0;j++){
                        if(this.elements[i][j]==this.elements[i][j+1]){
                            mergeRoutine(i,j);
                            stateChanged=true;
                            this.elements[i][j+1]=0;
                            for(int k=j+1;k<3;k++){
                                this.elements[i][k]=this.elements[i][k+1];
                                this.elements[i][k+1]=0;
                            }

                        }
                    }
                }
                break;
            case RIGHT:
                for(int i=0;i<4;i++){
                    for(int j=3;j>0 && this.elements[i][j-1]!=0;j--){
                        if(this.elements[i][j]==this.elements[i][j-1]){
                            mergeRoutine(i,j);
                            stateChanged=true;
                            this.elements[i][j-1]=0;
                            for(int k=j-1;k>0;k--){
                                this.elements[i][k]=this.elements[i][k-1];
                                this.elements[i][k-1]=0;
                            }

                        }
                    }
                }
                break;
            case UP:
                for(int j=0;j<4;j++){
                    for(int i=0;i<3 && this.elements[i+1][j]!=0;i++){
                        if(this.elements[i][j]==this.elements[i+1][j]){
                            mergeRoutine(i,j);
                            stateChanged=true;
                            this.elements[i+1][j]=0;
                            for(int k=i+1;k<3;k++){
                                this.elements[k][j]=this.elements[k+1][j];
                                this.elements[k+1][j]=0;
                            }

                        }
                    }
                }
                break;
            case DOWN:
                for(int j=0;j<4;j++){
                    for(int i=3;i>0 && this.elements[i-1][j]!=0;i--){
                        if(this.elements[i][j]==this.elements[i-1][j]){
                            mergeRoutine(i,j);
                            stateChanged=true;
                            this.elements[i-1][j]=0;
                            for(int k=i-1;k>0;k--){
                                this.elements[k][j]=this.elements[k-1][j];
                                this.elements[k-1][j]=0;
                            }

                        }
                    }
                }
                break;

        }
        return stateChanged;
    }

    private void mergeRoutine(int i,int j){
        this.elements[i][j]*=2;
        this.freeSpace++;
        this.score+=this.elements[i][j];
        if(this.elements[i][j]>this.pow(2,highestPower)){
            this.highestPower++;
        }
        if(this.elements[i][j]==2048){
            this.winCondition=true;
        }
    }

    private int pow (int a, int b)
    {
        if ( b == 0) return 1;
        if ( b == 1) return a;
        if (b%2==0) return pow ( a * a, b/2);
        else return a * pow ( a * a, b/2);

    }

    private void placeInRandom(){
        Random generator=new Random();
        int x= generator.nextInt(4);
        int y= generator.nextInt(4);
        for(int i=0;i<10 && this.elements[y][x]!=0;i++){
            x= generator.nextInt(4);
            y= generator.nextInt(4);
        }
        if(this.elements[y][x]!=0){
            List<Position> freeFields=new ArrayList<>();
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(this.elements[i][j]==0) freeFields.add(new Position(j,i));
                }
            }
            Position chosen=freeFields.get(generator.nextInt(freeFields.size()));
            x=chosen.x;
            y= chosen.y;
        }
        /* //Value generating according to Instructions
        if(highestPower>1) {
            this.elements[y][x] = pow(2, generator.nextInt(this.highestPower - 1) + 1);
        }else{
            this.elements[y][x]=2;
        }*/
        //Value generating according to actual game
        if(highestPower>1) {
            if(generator.nextInt(100)<75){
                this.elements[y][x]=2;
            }else{
                this.elements[y][x]=4;
            }
        }else{
            this.elements[y][x]=2;
        }

        this.freeSpace--;
        if(this.freeSpace==0){
            this.checkForLoss();
        }
    }


    private void checkForLoss(){
        this.lossCondition=true;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(this.elements[i][j]==this.elements[i][j+1] || this.elements[i][j]==this.elements[i+1][j]) this.lossCondition=false;
            }
        }
        for(int i=0;i<3;i++){
            if(this.elements[3][i]==this.elements[3][i+1] || this.elements[i][3]==this.elements[i+1][3]) this.lossCondition=false;
        }
    }

    public boolean getWinCondition(){
        return this.winCondition;
    }

    public boolean getLossCondition(){
        return this.lossCondition;
    }

    public int getScore(){ return this.score;}
    public int getElement(int i, int j){return this.elements[i][j];}

    public String toString(){
        StringBuilder result= new StringBuilder();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                result.append(this.elements[i][j]);
                result.append(" ");
            }
            result.append("\n");
        }
        result.append("--------------------");
        result.append("\n");
        return result.toString();
    }

}
