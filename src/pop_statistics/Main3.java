package pop_statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3
{

  static DeathStats deathGlobal = null;
  static DeathStats deathCovid = null;
  static Map<Integer, AgePyramid> pyramids = new HashMap<Integer, AgePyramid>();

  static int lastYear = 2022;
  static int firstYear = 1991;


  public static DeathStats loadDeathFromData() throws Exception
  {
    final String inputFolder = "E:/perso/data-deces/";
    DeathStats stats = new DeathStats( firstYear, lastYear );
    stats.loadDeathRecords( inputFolder + "deces-1991.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1992.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1993.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1994.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1995.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1996.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1997.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1998.txt" );
    stats.loadDeathRecords( inputFolder + "deces-1999.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2000.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2001.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2002.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2003.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2004.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2005.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2006.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2007.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2008.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2009.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2010.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2011.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2012.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2013.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2014.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2015.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2016.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2017.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2018.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2019.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2020.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2021.txt" );

    stats.loadDeathRecords( inputFolder + "deces-2022-t1.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2022-m04.txt" );
    stats.loadDeathRecords( inputFolder + "deces-2022-m05.txt" );

    return stats;
  }

  public static DeathStats loadDeathFromCovidData() throws Exception
  {
    DateTimeFormatter dateFormater = DateTimeFormatter.ISO_LOCAL_DATE;
    DeathStats stats = new DeathStats( firstYear, lastYear );

    FileReader fr = new FileReader( new File( "data/NombreDecesCOVID-19.csv" ) );
    BufferedReader br = new BufferedReader( fr );
    String line = null;
    while( (line = br.readLine()) != null )
    {
      String[] linedata = line.split( ";" );
      LocalDate date = LocalDate.from( dateFormater.parse( linedata[0] ) );
      stats.addMultipleDeaths( date.getYear(), date.getDayOfYear(), 0, true,
          Integer.parseInt( linedata[1] ) );
    }
    fr.close();

    return stats;
  }

  public static void printDeathPerAge() throws IOException
  {
    float extrapolation = (float)DeathStats.MAX_DAY_COUNT
        / (DeathStats.MAX_DAY_COUNT - deathGlobal.countDayWithoutDeath( lastYear ));
    Csv csv = new Csv();
    String[] headers = new String[lastYear - firstYear + 2];
    headers[0] = "Age";
    for( int i = 0; i <= lastYear - firstYear; i++ )
    {
      headers[i + 1] = "" + (firstYear + i);
    }
    csv.setHeaders( headers );
    for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
    {
      List<Object> row = new ArrayList<Object>();
      row.add( age );
      for( int year = firstYear; year <= lastYear; year++ )
      {
        long death = deathGlobal.getDeath( year, 0, DeathStats.MAX_DAY_COUNT, age, age, null );
        if( age == AgePyramid.MAX_AGE )
        {
          death = deathGlobal.getDeath( year, 0, DeathStats.MAX_DAY_COUNT, age, DeathStats.MAX_AGE,
              null );
        }
        long pop = pyramids.get( year ).get( age, age, null );
        if( year == lastYear )
        {
          row.add( extrapolation * death * 1000000f / pop );
        }
        else
        {
          row.add( death * 1000000f / pop );
        }
      }
      csv.addRow( row );
    }

    csv.print( "docs/results/DecesParAge.csv" );
  }


  public static void printDeathPerWeekAndAge() throws IOException
  {
    Csv csv = new Csv( "semaine \\ age", "0", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60",
        "61-70", "71-80", "81-90", "90+" );
    for( int year = 2019; year <= lastYear; year++ )
    {
      for( int day = 1; day < DeathStats.MAX_DAY_COUNT - 6; day += 7 )
      {
        List<Object> row = new ArrayList<Object>();
        String date = LocalDate.ofYearDay( year, day ).format( DateTimeFormatter.ISO_LOCAL_DATE );
        row.add( date );

        int fromAge = 0;
        int toAge = 0;
        long death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        long pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 1;
        toAge = 10;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 11;
        toAge = 20;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 21;
        toAge = 30;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 31;
        toAge = 40;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 41;
        toAge = 50;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 51;
        toAge = 60;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 61;
        toAge = 70;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 71;
        toAge = 80;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 81;
        toAge = 90;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 91;
        toAge = DeathStats.MAX_AGE;
        death = deathGlobal.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        csv.addRow( row );
      }
    }
    csv.print( "docs/results/DecesParSemaineEtClasseAge.csv" );
  }


  public static void printDeathPerWeek(int normalisedYear) throws IOException
  {
    Csv csv = new Csv( "semaine", "population", "deces", "deces covid", "taux",
        "taux normalise " + normalisedYear );
    for( int year = 2015; year <= lastYear; year++ )
    {
      for( int day = 1; day < DeathStats.MAX_DAY_COUNT - 6; day += 7 )
      {
        String date = LocalDate.ofYearDay( year, day ).format( DateTimeFormatter.ISO_LOCAL_DATE );
        long death = deathGlobal.getDeath( year, day, day + 6, 0, DeathStats.MAX_AGE, null );
        long covidDeath = deathCovid.getDeath( year, day, day + 6, 0, DeathStats.MAX_AGE, null );
        long pop = pyramids.get( year ).get( 0, AgePyramid.MAX_AGE, null );
        float rate = death * 1000000f / pop;

        // compute rate for every age
        long deathAge[] = new long[AgePyramid.MAX_AGE + 1];
        long popAge[] = new long[AgePyramid.MAX_AGE + 1];
        double rateAge[] = new double[AgePyramid.MAX_AGE + 1];
        for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
        {
          if( age == AgePyramid.MAX_AGE )
          {
            deathAge[age] = deathGlobal.getDeath( year, day, day + 6, age, DeathStats.MAX_AGE,
                null );
          }
          else
          {
            deathAge[age] = deathGlobal.getDeath( year, day, day + 6, age, age, null );
          }
          popAge[age] = pyramids.get( year ).get( age, age, null );
          rateAge[age] = deathAge[age] * 1000000d / popAge[age];
        }
        double normalisedRate = 0;
        for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
        {
          normalisedRate += rateAge[age] * pyramids.get( normalisedYear ).getRate( age, age, null );
        }

        csv.addRow( date, pop, death, covidDeath, rate, normalisedRate );
      }
    }
    csv.print( "docs/results/DecesParSemaine.csv" );
  }


  public static void printDeathPerYear(int normalisedYear) throws IOException
  {
    Csv csv = new Csv( "Annee", "population", "deces", "taux", "taux normalise " + normalisedYear );
    for( int year = firstYear; year <= lastYear; year++ )
    {
      long death = deathGlobal.getDeath( year, 1, DeathStats.MAX_DAY_COUNT, 0, DeathStats.MAX_AGE,
          null );
      long pop = pyramids.get( year ).get( 0, AgePyramid.MAX_AGE, null );
      float rate = death * 1000000f / pop;

      // compute rate for every age
      long deathAge[] = new long[AgePyramid.MAX_AGE + 1];
      long popAge[] = new long[AgePyramid.MAX_AGE + 1];
      double rateAge[] = new double[AgePyramid.MAX_AGE + 1];
      for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
      {
        if( age == AgePyramid.MAX_AGE )
        {
          deathAge[age] = deathGlobal.getDeath( year, 1, DeathStats.MAX_DAY_COUNT, age,
              DeathStats.MAX_AGE, null );
        }
        else
        {
          deathAge[age] = deathGlobal.getDeath( year, 1, DeathStats.MAX_DAY_COUNT, age, age, null );
        }
        popAge[age] = pyramids.get( year ).get( age, age, null );
        rateAge[age] = deathAge[age] * 1000000d / popAge[age];
      }
      double normalisedRate = 0;
      for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
      {
        normalisedRate += rateAge[age] * pyramids.get( normalisedYear ).getRate( age, age, null );
      }

      // extrapole last year
      if( year == lastYear )
      {
        float extrapolation = (float)DeathStats.MAX_DAY_COUNT
            / (DeathStats.MAX_DAY_COUNT - deathGlobal.countDayWithoutDeath( lastYear ));
        rate *= extrapolation;
        normalisedRate *= extrapolation;
      }

      csv.addRow( year, pop, death, rate, normalisedRate );
    }
    csv.print( "docs/results/DecesParAn.csv" );
  }



  public static void main(String[] args) throws Exception
  {
    pyramids = AgePyramid.load( "docs/results/PyramideDesAgesHommes.csv",
        "docs/results/PyramideDesAgesFemmes.csv" );
    deathGlobal = loadDeathFromData();
    deathGlobal.saveToJson( "docs/results/death-stats.json" );
    // deathGlobal = DeathStats.loadFromJson( "docs/results/death-stats.json" );
    deathCovid = loadDeathFromCovidData();

    printDeathPerWeek( 2021 );
    printDeathPerYear( 2021 );
    printDeathPerWeekAndAge();
    printDeathPerAge();


  }

}
