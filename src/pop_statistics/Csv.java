package pop_statistics;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Csv
{
  public List<String> colHeaders = new ArrayList<String>();
  public List<List<String>> data = new ArrayList<List<String>>();


  public Csv(String... headers)
  {
    this.setHeaders( headers );
  }

  public void setHeaders(String... headers)
  {
    colHeaders.clear();
    for( String header : headers )
    {
      colHeaders.add( header );
    }
  }

  public int getColIndex(String header)
  {
    return colHeaders.indexOf( header );
  }


  public void addRow(Object... values)
  {
    List<String> row = new ArrayList<String>();
    for( Object value : values )
    {
      if( value instanceof Float || value instanceof Double )
      {
        row.add( String.valueOf( value ).replace( '.', ',' ) );
      }
      else if( value instanceof Collection )
      {
        addRow( ((Collection)value).toArray() );
        return;
      }
      else
      {
        row.add( String.valueOf( value ) );
      }
    }
    data.add( row );
  }

  /**
   * 
   * @param rowIndex zero based
   * @param columnIndex zero based
   */
  public void set(int rowIndex, int columnIndex, String value)
  {
    while( rowIndex >= data.size() )
    {
      data.add( new ArrayList<String>() );
    }
    List<String> row = data.get( rowIndex );
    while( columnIndex >= row.size() )
    {
      row.add( "" );
    }
    row.set( columnIndex, value );
  }

  public String get(int rowIndex, int columnIndex)
  {
    if( rowIndex < 0 || rowIndex >= data.size() )
    {
      return "";
    }
    List<String> row = data.get( rowIndex );
    if( columnIndex < 0 || columnIndex >= row.size() )
    {
      return "";
    }
    return row.get( columnIndex );
  }


  public void print(String strFile) throws IOException
  {
    File file = new File( strFile );
    PrintStream fw = new PrintStream( file );
    print( fw );
    fw.close();
    System.out.println( "write to " + file.getCanonicalPath() );
  }


  public void print(PrintStream stream)
  {
    if( colHeaders.size() > 0 )
    {
      for( String header : colHeaders )
      {
        stream.print( header );
        stream.print( ';' );
      }
      stream.println();
    }
    for( List<String> row : data )
    {
      for( String value : row )
      {
        stream.print( value );
        stream.print( ';' );
      }
      stream.println();
    }
  }
}
