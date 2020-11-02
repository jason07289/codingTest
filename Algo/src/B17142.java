import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class B17142{
    static int n;
    static int m;
    static int[][] map;
    static int[][] map2;
    static int[][] timeMap;
    static int[] choice;
    static int size;

    static int dx[] = {0,0,-1,1};
    static int dy[] = {1,-1,0,0};
    static int minResult;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        minResult = Integer.MAX_VALUE;
        int setVir = 2;
        map = new int[n][n];
        map2 = new int[n][n];
        timeMap = new int[n][n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                int val = Integer.parseInt(st.nextToken());
                if(val==2){
                    map[i][j] = setVir;
                    setVir++;
                }else{
                    map[i][j] = val;

                }
                

            }
        }

        for(int i=0; i<n; i++){
            map2[i] = map[i].clone();
        }

        size= setVir-2;
        choice = new int[size];

        // System.out.println();
        // for(int i=0; i<n; i++){
        //     System.out.println(Arrays.toString(map2[i]));
        // }
        // System.out.println();
   
        test(0,0);
//        System.out.println("ans");
        if(minResult==Integer.MAX_VALUE) {
        	System.out.println(-1);
        }else
        	System.out.println(minResult);
    }

    public static void resetMap(){
        for(int i=0; i<n; i++){
            map[i] = map2[i].clone();
        }
        
    }

    public static int checkMap(){
        int value=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]==0){
                    value = Integer.max(value,timeMap[i][j]);
                }
            }
        }

        return value;
    }

    public static boolean checkZero(){
        boolean flag=true;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(timeMap[i][j]==0&&map[i][j]==0){
                    flag=false;
                }
            }
        }

        return flag;
    }

    public static void start(){
        

        for(int c=0; c<size; c++){//바이러스 활성화
            if(choice[c]==1){
                for(int i=0; i<n; i++){
                    for(int j=0; j<n; j++){
                        if(map[i][j]==c+2){
                            map[i][j]=-1;
                        }
                    }
                }
            }
        }

        search();
    }

    public static void search(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]==-1){
                    spread(i,j);
                }
            }
        }
    }

    public static void spread(int xx, int yy){

        Queue<Obj> q = new LinkedList<>();
        q.add(new Obj(xx, yy, 0));
        
        while(!q.isEmpty()){

            Obj o = q.poll();
            for(int i=0; i<4; i++){
                int nx = o.x+dx[i];
                int ny = o.y+dy[i];
                int no = o.len+1;// 1 
                if(nx>=0&&ny>=0&&nx<n&&ny<n){
                    if(map[nx][ny]!=-1&&map[nx][ny]!=1){
                        if(timeMap[nx][ny]>no||timeMap[nx][ny]==0){
                            if(map[nx][ny]>=2){
                                timeMap[nx][ny]=no;
                                q.add(new Obj(nx, ny, no));
                            }else{
                                timeMap[nx][ny]=no;
                                q.add(new Obj(nx, ny, no));
                            }
                            

                        }
                    }
                }
            }
        }


                
    }

    public static void test(int depth,int virCnt){
        if(virCnt>m){
            return;
        }

        if(depth==size&&virCnt==m){
            
            // System.out.println(Arrays.toString(choice));
            start();
//            System.out.println("===============map=============");
//            for(int i=0; i<n; i++){
//                System.out.println(Arrays.toString(map[i]));
//            }
//            System.out.println();
//            System.out.println("===============ans=============");
//            for(int i=0; i<n; i++){
//                System.out.println(Arrays.toString(timeMap[i]));
//            }
//            System.out.println();
            
            if(checkZero()){

                int result =checkMap();
//                System.out.println("tmpAns: "+result);
                minResult=Integer.min(minResult, result);

            }
            
            timeMap = new int[n][n];
            resetMap();
            
            return;
        }
        if(depth==size){
 
            return;
        }

        
        for(int i=0; i<=1; i++){
            
            choice[depth]=i;
            if(i==1){
                virCnt++;
            }
            test(depth+1,virCnt);
        }

    }

    public static class Obj{
        int x;
        int y;
        int len;
        public Obj(int x, int y, int len){
            this.x = x;
            this.y = y;
            this.len = len;
        }
    }

}