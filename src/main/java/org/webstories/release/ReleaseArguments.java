package org.webstories.release;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> getDeclaredTasks() {
		List<String> result = new ArrayList<String>();
		for ( String argument : arguments ) {
			String firstChar = argument.charAt( 0 ) + "";
			String lastChar = argument.charAt( argument.length() - 1 ) + "";
			String taskDeclRegex = "[a-zA-Z0-9]";
			if ( firstChar.matches( taskDeclRegex ) && lastChar.matches( taskDeclRegex ) ) {
				result.add( argument );
			}
		}
		return result;
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
