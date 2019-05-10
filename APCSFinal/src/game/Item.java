package game;
public class Item {
		private int x,y;
		private int type;
		public Item(int x, int y, int type) {
			this.x=x;
			this.y=y;
			this.type=type;
		}
		/**
		 * Generates an Item with a random type
		 * @param xCoord x coordinate of center;
		 * @param yCoord y coordinate of center;
		 */
		public Item(int xCoord, int yCoord)
		{
			x=xCoord;
			y=yCoord;
			int t=(int)(Math.random()*4);
			type=t;
		}
		public int getX()
		{
			return x;
		}
		public int getY()
		{
			return y;
		}
		public int getType()
		{
			return type;
		}
}
