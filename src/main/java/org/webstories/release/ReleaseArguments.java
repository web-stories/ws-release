package org.webstories.release;

public class ReleaseArguments {
	private String[] arguments;
	
	public ReleaseArguments( String[] arguments ) {
		this.arguments = arguments;
	}
	
	public String getValue( String argumentName ) throws ArgumentNotFoundException {
		for ( String argument : arguments ) {
			String[] pair = argument.split( "=" );
			if ( pair.length == 1 ) {
				continue;
			}
			String key = pair[ 0 ];
			String value = pair[ 1 ];
			
			if ( !key.equals( "--" + argumentName ) ) {
				continue;
			}
			
			if ( value.startsWith( "\"" ) && value.endsWith( "\"" ) ) {
				value = value.substring( 1, value.length() - 1 );
			}
			
			return value;
		}
		throw new ArgumentNotFoundException();
	}
}
