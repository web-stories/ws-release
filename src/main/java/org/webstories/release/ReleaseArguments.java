package org.webstories.release;

import org.eclipse.jdt.annotation.Nullable;

public class ReleaseArguments {
	private String[] arguments;
	
	public ReleaseArguments( String[] arguments ) {
		this.arguments = arguments;
	}
	
	public String getValue( String name ) throws ArgumentNotFoundException {
		String argument = getArgument( name );
		
		if ( argument == null ) {
			throw new ArgumentNotFoundException();
		}
		
		if ( !argument.contains( "=" ) ) {
			return "";
		}
		
		String[] pair = argument.split( "=" );
		String value = pair[ 1 ];
		
		if ( value.startsWith( "\"" ) && value.endsWith( "\"" ) ) {
			value = value.substring( 1, value.length() - 1 );
		}
		
		return value;
	}
	
	public boolean contains( String name ) {
		return getArgument( name ) != null;
	}
	
	private @Nullable String getArgument( String name ) {
		for ( String argument : arguments ) {
			String key = argument.split( "=" )[ 0 ];
			
			if ( key.equals( "--" + name ) ) {
				return argument;
			}
		}
		return null;
	}
}
