package classes;

import java.io.IOException;
import java.net.MalformedURLException;

public class youtune
{

    public static void main(String[] args)
    {
	Search search = null;
	try
	{
	    search = new Search(args[0]);
	} catch (MalformedURLException e1)
	{
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	try
	{
	    search.getVideoList();
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
