/**
 * 
 */
package pop_statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * TODO add two stats to add new death records
 *
 */
public class DeathStats
{
  public static int MAX_AGE = 120;
  public static int MAX_DAY_COUNT = 366;

  protected long version = 1L;

  protected int fromYear = 0;
  protected int toYear = 0;

  // week, age
  protected long[][] maleDeathCount = null;
  protected long[][] femaleDeathCount = null;

  protected Map<Integer, Long> ignoredRecords = new HashMap<Integer, Long>();
  protected List<String> metadata = new ArrayList<String>();

  /**
   * for gson only
   */
  @SuppressWarnings("unused")
  private DeathStats()
  {
  }

  public DeathStats(int fromYear, int toYear)
  {
    this.fromYear = fromYear;
    this.toYear = toYear;
    int totalDayCount = (this.toYear + 1 - this.fromYear) * MAX_DAY_COUNT;
    this.maleDeathCount = new long[totalDayCount][MAX_AGE + 1];
    this.femaleDeathCount = new long[totalDayCount][MAX_AGE + 1];
  }

  /**
   * 
   * @param year between configured from an to year (included)
   * @param day  from 1 to 366 (included)
   * @return
   */
  protected int getDayIndex(int year, int day)
  {
    assert year >= fromYear && year <= toYear;
    assert day > 0 && day <= MAX_DAY_COUNT;
    int index = (year - fromYear) * MAX_DAY_COUNT + (day - 1);
    assert index >= 0 && index < this.maleDeathCount.length;
    return index;
  }

  /**
   * 
   * @param deathYear
   * @param deathDay  from 1 to 366 (included)
   * @param age
   * @param isMale
   */
  public void addDeathRecord(int deathYear, int deathDay, int age, boolean isMale)
  {
    if( age > MAX_AGE )
    {
      age = MAX_AGE;
    }
    if( age < 0 || deathDay <= 0 || deathDay > MAX_DAY_COUNT || deathYear < fromYear
        || deathYear > toYear )
    {
      Long count = ignoredRecords.get( deathYear );
      if( count == null )
      {
        count = Long.valueOf( 0 );
      }
      count++;
      ignoredRecords.put( deathYear, count );
    }
    else
    {
      if( isMale )
      {
        this.maleDeathCount[getDayIndex( deathYear, deathDay )][age]++;
      }
      else
      {
        this.femaleDeathCount[getDayIndex( deathYear, deathDay )][age]++;
      }
    }
  }

  public void addMultipleDeaths(int deathYear, int deathDay, int age, boolean isMale,
      int deathCount)
  {
    if( age > MAX_AGE )
    {
      age = MAX_AGE;
    }
    if( age < 0 || deathDay <= 0 || deathDay > MAX_DAY_COUNT || deathYear < fromYear
        || deathYear > toYear )
    {
      Long count = ignoredRecords.get( deathYear );
      if( count == null )
      {
        count = Long.valueOf( 0 );
      }
      count += deathCount;
      ignoredRecords.put( deathYear, count );
    }
    else
    {
      if( isMale )
      {
        this.maleDeathCount[getDayIndex( deathYear, deathDay )][age] += deathCount;
      }
      else
      {
        this.femaleDeathCount[getDayIndex( deathYear, deathDay )][age] += deathCount;
      }
    }
  }

  /**
   * 
   * @param rec
   * @return true if record is used
   */
  public void addDeathRecord(DeathRecord rec)
  {
    addDeathRecord( rec.death.getYear(), rec.death.getDayOfYear(), rec.getDeathAge(),
        rec.isMale() );
  }

  public void addMetaData(String data)
  {
    metadata.add( data );
  }

  public List<String> getMetaData()
  {
    return metadata;
  }

  public Map<Integer, Long> getIgnoredRecords()
  {
    return ignoredRecords;
  }

  public long countIgnoredRecords()
  {
    long ignored = 0;
    for( Long count : getIgnoredRecords().values() )
    {
      ignored += count;
    }
    return ignored;
  }

  public void loadDeathRecords(String strFile) throws Exception
  {
    System.out.println( "load " + strFile );
    File file = new File( strFile );
    FileReader fr = new FileReader( file );
    loadDeathRecords( fr, strFile );
    fr.close(); // closes the stream and release the resources
  }


  public void loadDeathRecords(Reader reader, String strFile) throws Exception
  {
    long errorCount = 0;
    long recordCount = 0;
    long ignoredRecords = countIgnoredRecords();
    BufferedReader br = new BufferedReader( reader ); // creates a buffering
                                                      // character input stream
    long lineCount = 0;
    String line;
    while( (line = br.readLine()) != null )
    {
      DeathRecord rec = DeathRecord.parse( line );
      if( rec != null )
      {
        addDeathRecord( rec );
        recordCount++;
      }
      else
      {
        System.out.println( "" + lineCount + "  " + line );
        errorCount++;
      }
      lineCount++;
    }
    br.close(); // closes the stream and release the resources
    ignoredRecords = countIgnoredRecords() - ignoredRecords;
    String metadata = "end loading '" + strFile + "' with " + recordCount + " records";
    if( ignoredRecords > 0 )
    {
      metadata += ", " + ignoredRecords + " ignored";
    }
    if( errorCount > 0 )
    {
      metadata += ", " + errorCount + " error";
    }
    addMetaData( metadata );
    System.out.println( metadata );
  }


  public void saveToJson(String strFile) throws IOException
  {
    File file = new File( strFile );
    FileWriter fw = new FileWriter( file );
    new Gson().toJson( this, fw );
    fw.close();
    System.out.println( "write stats to " + file.getCanonicalPath() );


  }

  public static DeathStats loadFromJson(String strFile) throws Exception
  {
    File file = new File( strFile );
    System.out.println( "load " + file.getCanonicalPath() );
    FileReader fr = new FileReader( file );
    DeathStats stats = new Gson().fromJson( fr, DeathStats.class );
    fr.close();
    System.out.println( "deathcount:" + stats.getAllDeath() );
    return stats;
  }



  public long getAllDeath()
  {
    long count = 0;
    for( long[] deathPerWeek : maleDeathCount )
    {
      for( long death : deathPerWeek )
      {
        count += death;
      }
    }
    for( long[] deathPerWeek : femaleDeathCount )
    {
      for( long death : deathPerWeek )
      {
        count += death;
      }
    }
    return count;
  }


  public int countDayWithoutDeath(int year)
  {
    int count = 0;
    for( int iDay = 1; iDay <= MAX_DAY_COUNT; iDay++ )
    {
      if( getDeath( year, iDay, iDay, 0, MAX_AGE, null ) < 10 )
      {
        count++;
      }
    }
    return count;
  }

  public long getDeath(int year, int day, int age, Boolean isMale)
  {
    if( age < 0 || day <= 0 || day > MAX_DAY_COUNT || year < fromYear || year > toYear )
    {
      return 0;
    }
    long count = 0;
    if( isMale == null || isMale == true )
    {
      count += this.maleDeathCount[getDayIndex( year, day )][age];
    }
    if( isMale == null || isMale == false )
    {
      count += this.femaleDeathCount[getDayIndex( year, day )][age];
    }
    return count;
  }

  public long getDeath(int year, int fromDay, int toDay, int fromAge, int toAge, Boolean isMale)
  {
    long count = 0;
    for( int iDay = fromDay; iDay <= toDay; iDay++ )
    {
      for( int iAge = fromAge; iAge <= toAge; iAge++ )
      {
        count += getDeath( year, iDay, iAge, isMale );
      }
    }
    return count;
  }
}
