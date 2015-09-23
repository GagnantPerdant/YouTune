package classes;

import java.net.MalformedURLException;
import java.net.URL;

public class Video
{
    private String id;
    private String title;
    private String idAuthor;
    private String length;
    
    public Video(String url) throws Exception
    {
	super();
	//System.out.println("create video from: " + url);
	this.id = url.replace("/watch?v=","");
	if(id.contains("http"))
	{
	    throw new Exception("bad url");
	}
    }
    
    public URL getUrl() throws MalformedURLException
    {
	return new URL("https://www.youtube.com/watch?v=" + this.id);
    }

    public String getTitle()
    {
        return title;
    }
    
    public String getLength()
    {
        return length;
    }

    public void setTitle(String title)
    {
	//System.out.println(title);
	try
	{
	String separator = "Durée :";
	String separator2 = "- Durée :";
        this.title = title.split(separator2)[0].trim();
        this.length = title.split(separator)[1].trim();
	}
	catch(Exception e)
	{
	    this.length = "??:??";
	}
    }

    public String getIdAuthor()
    {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor)
    {
        this.idAuthor = idAuthor;
    }

    public String getId()
    {
        return id;
    }
    
    
}
