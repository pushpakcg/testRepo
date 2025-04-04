import java.io.File;
public class Filesclear {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f= new File("C:\\Users\\kn115852\\Documents\\Clear");
		String[] s=f.list();
		for(String s1:s)
		{
			File f1=new File(f,s1);
			f1.delete();
		}
  System.out.println("all files are delete successfully");
	}

}
