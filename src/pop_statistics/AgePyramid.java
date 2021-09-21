package pop_statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class AgePyramid
{
  final static int MAX_AGE = 100;

  int year = 0;
  long[] maleCount = new long[MAX_AGE + 1];
  long[] femaleCount = new long[MAX_AGE + 1];

  public int getYear()
  {
    return year;
  }

  public void add(int age, boolean isMale, long count)
  {
    assert age >= 0;
    if( age >= MAX_AGE )
    {
      age = MAX_AGE;
    }
    if( isMale )
    {
      maleCount[age] += count;
    }
    else
    {
      femaleCount[age] += count;
    }
  }



  /**
   * read several year from two csv file
   */
  public static Map<Integer, AgePyramid> load(String menCsv, String womenCsv) throws Exception
  {
    return load( new File( menCsv ), new File( womenCsv ) );
  }


  public static Map<Integer, AgePyramid> load(File mewCsv, File womenCsv) throws Exception
  {
    Map<Integer, AgePyramid> pyramids = new HashMap<Integer, AgePyramid>();

    FileReader fr = new FileReader( mewCsv );
    BufferedReader br = new BufferedReader( fr );
    String line = br.readLine();
    String[] headers = line.split( ";" );
    int[] menYears = new int[headers.length];
    for( int col = 1; col < headers.length; col++ )
    {
      menYears[col] = Integer.parseInt( headers[col] );
      AgePyramid pyramid = new AgePyramid();
      pyramid.year = menYears[col];
      pyramids.put( pyramid.getYear(), pyramid );
    }

    while( (line = br.readLine()) != null )
    {
      String[] linedata = line.split( ";" );
      int age = Integer.parseInt( linedata[0].trim() );
      for( int column = 1; column < linedata.length; column++ )
      {
        long count = Long.parseLong( linedata[column].trim() );
        pyramids.get( menYears[column] ).maleCount[age] = count;
      }
    }
    fr.close();


    fr = new FileReader( womenCsv );
    br = new BufferedReader( fr );
    line = br.readLine();
    headers = line.split( ";" );
    int[] womenYears = new int[headers.length];
    for( int col = 1; col < headers.length; col++ )
    {
      womenYears[col] = Integer.parseInt( headers[col] );
      if( womenYears[col] != menYears[col] )
      {
        br.close();
        throw new Exception( "the two csv file are not cooherant" );
      }
    }
    while( (line = br.readLine()) != null )
    {
      String[] linedata = line.split( ";" );
      int age = Integer.parseInt( linedata[0].trim() );
      for( int column = 1; column < linedata.length; column++ )
      {
        long count = Long.parseLong( linedata[column].trim() );
        pyramids.get( womenYears[column] ).femaleCount[age] = count;
      }
    }
    fr.close();


    return pyramids;
  }

  public void write(Writer writer) throws IOException
  {
    for( int age = 0; age <= MAX_AGE; age++ )
    {
      int birth = getYear() - 1 - age;
      writer.write(
          "" + birth + ",\t" + age + ",\t" + maleCount[age] + ",\t" + femaleCount[age] + "\n" );
    }
  }

  public long getAll()
  {
    return getAll( null );
  }

  public long getAll(Boolean isMale)
  {
    long all = 0;
    if( isMale == null || isMale == true )
    {
      for( Long count : maleCount )
      {
        all += count;
      }
    }
    if( isMale == null || isMale == false )
    {
      for( Long count : femaleCount )
      {
        all += count;
      }
    }
    return all;
  }

  public long get(int age, Boolean isMale)
  {
    long count = 0;
    if( age >= 0 && age <= MAX_AGE )
    {
      if( isMale == null || isMale == true )
      {
        count += maleCount[age];
      }
      if( isMale == null || isMale == false )
      {
        count += femaleCount[age];
      }
    }
    return count;
  }

  public long get(int fromAge, int toAge, Boolean isMale)
  {
    long count = 0;
    for( int iAge = fromAge; iAge <= toAge; iAge++ )
    {
      count += get( iAge, isMale );
    }
    return count;
  }


  public float getRate(int fromAge, int toAge, Boolean isMale)
  {
    return ((float)get( fromAge, toAge, isMale )) / getAll( isMale );
  }



}
