package classes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Search
{
    private URL url;
    private final String
    	type = "&lclk=video",
  	longs = "&filters=long",
    	shorts = "&filters=short",
	voted = "&search_sort=video_view_count";

    public Search(String query) throws MalformedURLException
    {
	super();
	query = "https://www.youtube.com/results?search_query="
		+ query.replace(" ", "+") ;
	this.url = new URL(query);
    }
    
    public URL getQuery()
    {
	return this.url;
    }

    public URL getQueryLong() throws MalformedURLException
    {
	return new URL(this.url.toString() + type + longs);
    }

    public URL getQueryShort() throws MalformedURLException
    {
	return new URL(this.url.toString() + type + shorts);
    }

    public URL getQueryVoted() throws MalformedURLException
    {
	return new URL(this.url.toString() + type + voted);
    }

    public URL getUrlPage(URL url, int nb) throws MalformedURLException
    {
	if (nb < 10 && nb > 0)
	{
	    return new URL(url.toString() + "&page=" + nb);
	} else
	{
	    return new URL(url.toString());
	}
    }

    public List<Video> getVideoList() throws IOException
    {
	ArrayList<Video> list = new ArrayList<Video>();
	Connection conn;
	FenetrePrincipale fen = new FenetrePrincipale();
	
	List<String> urls = new ArrayList<String>();
	for (int i = 1; i <= 2; i++)
	{
	    urls.add(this.getUrlPage(this.getQueryLong(),i).toString());
	}
	for (int i = 1; i <= 2; i++)
	{
	    urls.add(this.getUrlPage(this.getQueryShort(),i).toString());
	}
	for (int i = 1; i <= 2; i++)
	{
	    urls.add(this.getUrlPage(this.getQueryVoted(),i).toString());
	}
	
	for (String listurl : urls)
	{
	    conn = Jsoup.connect(listurl);
	    conn.followRedirects(true);
	    conn.timeout(20000);
	    conn.ignoreHttpErrors(true);
	    conn.userAgent("Windows / IE 10: Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
	    Document doc = conn.get();
	    Response resp = conn.response();
	    System.out.printf("Response %s %s... %s\n", resp.statusCode(),
		    resp.statusMessage(), doc.location());
	    Elements elements = doc.select("[href*=watch]");
	    if(elements.isEmpty())
	    {
		break;
	    }
	    for (Element element : elements)
	    {
		if (!element.attr("title").isEmpty())
		{
		    try
		    {
		    Video video = new Video(element.attr("href"));
		    video.setTitle(element.attr("title"));
		    //System.out.println(video.getTitle() + " => " + video.getUrl());
		    list.add(video);
		    fen.addToListVideo(video);
		    }
		    catch(Exception e)
		    {
			/* TODO */
		    }
		}
	    }
	}
	return list;
    }
}
