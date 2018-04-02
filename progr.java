package get_diavgeia;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
public class progr {
	static String mainh="https://diavgeia.gov.gr/opendata/search/advanced?q=";
	static String query="subject:\"ΓΝΩΜΟΔΟΤΗΣΗ\"ANDorganizationUid:\"50024\"";
	static String page="&page=0";
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
	      return result.toString();
	   }
	public static void main(String[] args)
	{
		int i=0;
		System.out.println("START");
		try {
			//String res = getHTML("https://diavgeia.gov.gr/opendata/search/advanced?q=subject:%22%CE%93%CE%9D%CE%A9%CE%9C%CE%9F%CE%94%CE%9F%CE%A4%CE%97%CE%A3%CE%97%22ANDorganizationUid:%2250024%22");
			String res = getHTML(mainh+query+page);
			String[] tok = res.split(",");
			for(String s:tok)
			{
				if(s.startsWith("\"documentUrl\"")==true)
				{
					String[] p = s.split("\"");
					//System.out.println(p.length);
					if(p.length>3)
					{
						System.out.println(p[3]);
						String durl=p[3];
						String fname=query+"_N"+i;
						fname = fname.replaceAll("[\\\\/:*?\"<>|]", "");
						downloadUsingNIO(durl, "C:/Users/Public/Documents/"+fname+".pdf");
						i+=1;
					}
				}
			}
			System.out.println(i+ " documents");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
