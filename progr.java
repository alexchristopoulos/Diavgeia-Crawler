package get_diavgeia;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
public class progr {
	static String mainh="https://diavgeia.gov.gr/opendata/search/advanced?q=";
	static String query="decisionType:\"ΓΝΩΜΟΔΟΤΗΣΗ\"ANDorganizationUid:\"50024\"";
	static String page="&page=0";
	static final int pnum=18;
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      System.out.println("RESPONSE OK");
	      return result.toString();
	   }
	public static void main(String[] args)
	{
		int i=0;
		System.out.println("START");
			for(int u=1;u<=pnum;u++)
			{
				try {
			String res = getHTML(mainh+query+page);
			String[] tok = res.split(",");
			page="&page="+Integer.toString(u-1);
			System.out.println("At page:" + mainh+query+page);
			for(String s:tok)
			{
				if(s.startsWith("\"documentUrl\"")==true)
				{
					String[] p = s.split("\"");
					//System.out.println(p.length);
					if(p.length>3)
					{
						String durl=p[3];
						String fname=getOriginalName(durl);
						fname = fname.replaceAll("[\\\\/:*?\"<>|]", "");
						try {
						downloadUsingNIO(durl, "C:/Users/Public/Documents/"+fname+".pdf");
						i+=1;
						}catch(Exception oo)
						{
							
						}
						
					}
				}
			}
			}
				catch (Exception e) {
					
				}
			
		} 
			System.out.println(i+ " documents");
	}
	static String getOriginalName(String url)
	{
		String res="";
		int i=0;
		char[] tmp = url.toCharArray();
		for(char c:tmp)
		{
			if(i==4)
			{
				res=res+c;
			}
			if(c=='/')
			{
				i+=1;
			}
			
		}
		return res;
	}
	 private static void downloadUsingNIO(String urlStr, String file) throws IOException {
	        URL url = new URL(urlStr);
	        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
	        FileOutputStream fos = new FileOutputStream(file);
	        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	        fos.close();
	        rbc.close();
	    }
}
