import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B19238 {
	static int n;
	static int m;
	static Taxi taxi;
	static int[][] pmap;
	static int[][] tmap;
	static boolean visited[][];
	static int dx[] = {0,0,-1,1};
	static int dy[] = {1,-1,0,0};
	static int dis;
	static boolean getPerson;
	static boolean getTarget;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		int fuel = Integer.parseInt(st.nextToken());
		
		pmap = new int[n][n];
		tmap = new int[n][n];
		
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				pmap[i][j] = Integer.parseInt(st.nextToken());
				tmap[i][j] = pmap[i][j];
			}
		}
		st = new StringTokenizer(br.readLine());
		int taxiX = Integer.parseInt(st.nextToken())-1;
		int taxiY = Integer.parseInt(st.nextToken())-1;
		pmap[taxiX][taxiY] =2;
		taxi = new Taxi(taxiX, taxiY, fuel);
		
		int personNum=3;
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int px = Integer.parseInt(st.nextToken())-1;
			int py = Integer.parseInt(st.nextToken())-1;
			int tx = Integer.parseInt(st.nextToken())-1;
			int ty = Integer.parseInt(st.nextToken())-1;
			
			pmap[px][py] = personNum;
			tmap[tx][ty] = personNum*-1;
			personNum++;
		}
		
//		for(int i=0; i<n; i++) {
//			System.out.println(Arrays.toString(map[i]));
//		}
		
		while(m!=0) {
			visited = new boolean[n][n];
			getPerson=false;
			findPerson(taxi.x, taxi.y);
//			System.out.println(taxi.pnum );
//			System.out.println(dis);
			if(!getPerson) {
				taxi.fuel = -1;
				break;
			}
			m-=1;//ÇÑ¸íÅÂ¿ü°í
			taxi.fuel -=dis;
//			System.out.println("¼Õ´ÔÅÂ¿î ÈÄ fuel: "+ taxi.fuel);
			visited = new boolean[n][n];
			
			
			getTarget=false;
			findTarget(taxi.x, taxi.y);
			taxi.fuel -=dis;
			if(taxi.fuel<0) {
				taxi.fuel = -1;
				break;
			}
			if(!getTarget) {
				taxi.fuel = -1;
				break;
			}
			taxi.fuel +=dis*2;
//			System.out.println("¼Õ´Ô³»¸° ÈÄ fuel: "+ taxi.fuel);
			
		}
		System.out.println(taxi.fuel);
	}
	
	private static void findTarget(int tx, int ty) {
		PriorityQueue<Route> pq = new PriorityQueue<>();
		pq.add(new Route(tx, ty, 0));
		
		while(!pq.isEmpty()) {
			Route r = pq.poll();
//			System.out.println(r);
			visited[r.x][r.y]=true;
			if(tmap[r.x][r.y]==taxi.pnum*-1) {
				getTarget = true;
				taxi.pnum = tmap[r.x][r.y];
				tmap[r.x][r.y]=0;
				dis = r.distance;
				taxi.x = r.x;
				taxi.y = r.y;
				break;
			}
			for(int i=0; i<4; i++) {
				int nx = r.x + dx[i]; 
				int ny = r.y + dy[i]; 
				if(nx<n&&ny<n&&nx>=0&&ny>=0) {
					if(tmap[nx][ny]!=1&&!visited[nx][ny]) {
						pq.add(new Route(nx,ny,r.distance+1));
					}
				}
			}
			
		}
		
	}

	private static void findPerson(int tx, int ty) {
		PriorityQueue<Route> pq = new PriorityQueue<>();
		pq.add(new Route(tx, ty, 0));
		
		while(!pq.isEmpty()) {
			Route r = pq.poll();
//			System.out.println(r);
			visited[r.x][r.y]=true;
			if(pmap[r.x][r.y]>2) {
				getPerson = true;
				taxi.pnum = pmap[r.x][r.y];
				pmap[r.x][r.y]=0;
				dis = r.distance;
				taxi.x = r.x;
				taxi.y = r.y;
				break;
			}
			for(int i=0; i<4; i++) {
				int nx = r.x + dx[i]; 
				int ny = r.y + dy[i]; 
				if(nx<n&&ny<n&&nx>=0&&ny>=0) {
					if(pmap[nx][ny]!=1&&!visited[nx][ny]) {
						pq.add(new Route(nx,ny,r.distance+1));
					}
				}
			}
			
		}
		
	}
	
	static class Route implements Comparable<Route>{
		int x;
		int y;
		int distance;
		public Route(int x, int y, int distance) {
			super();
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
		
		
		@Override
		public String toString() {
			return "Route [x=" + x + ", y=" + y + ", distance=" + distance + "]";
		}


		@Override
		public int compareTo(Route o) {
			if(this.distance>o.distance) {
				return 1;
			}else if(this.distance<o.distance){
				return -1;
			}else {
				if(this.x>o.x) {
					return 1;
				}else if(this.x<o.x) {
					return -1;
				}else {
					if(this.y>o.y) {
						return 1;
					}else {
						return -1;
					}
				}
			}
		}
		
	}
	
	static class Taxi{
		int x;
		int y;
		int fuel;
		int pnum;
		
		
		
		@Override
		public String toString() {
			return "Taxi [x=" + x + ", y=" + y + ", fuel=" + fuel + ", pnum=" + pnum + "]";
		}



		public Taxi(int x, int y, int fuel) {
			super();
			this.x = x;
			this.y = y;
			this.fuel = fuel;
		}
		
	}
}
