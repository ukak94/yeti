/*
 * Yeti 2, NesC development in Eclipse.
 * Copyright (C) 2009 ETH Zurich
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Web:  http://tos-ide.ethz.ch
 * Mail: tos-ide@tik.ee.ethz.ch
 */
package tinyos.yeti.ep.parser;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * A set of {@link Tag}s, can be used to easily compare two set of tags.
 * @author Benjamin Sigg
 * @see Tag
 * @see IASTModel
 */
public class TagSet implements Iterable<Tag>{
    public static final TagSet EMPTY = EmptyTagSet.EMPTY;
    
    /**
     * Gets a set that contains all tags of <code>tags</code>.
     * @param tags a list of tags
     * @return the set of tags
     */
    public static TagSet get( Tag... tags ){
        TagSet set = new TagSet();
        for( Tag tag : tags )
            set.add( tag );
        return set;
    }

    /*
     * For now we are using a TreeSet internally, but that might change
     * in the future.
     */

    private TreeSet<Tag> set = new TreeSet<Tag>();

    public boolean add( Tag tag ){
        return set.add( tag );
    }
    
    /**
     * Adds <code>tag</code> to this but only if
     * <code>tags</code> contains <code>tag</code>.
     * @param tags the set of available tags
     * @param tag the tag that might get copied from <code>tags</code>
     */
    public void add( TagSet tags, Tag tag ){
        if( tags.contains( tag ))
            add( tag );
    }

    public void remove( Tag tag ){
        set.remove( tag );
    }
    
    public void remove( TagSet tags ){
    	if( tags != null ){
    		for( Tag tag : tags ){
    			remove( tag );
    		}
    	}
    }

    public boolean contains( Tag tag ){
        return set.contains( tag );
    }

    public boolean contains( TagSet tags ){
        return set.containsAll( tags.set );
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( set == null ) ? 0 : set.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass() != obj.getClass() )
            return false;
        final TagSet other = (TagSet)obj;
        if( set == null ) {
            if( other.set != null )
                return false;
        } else if( !set.equals( other.set ) )
            return false;
        return true;
    }

    /**
     * Counts how many tags from <code>tags</code> are in 
     * <code>this</code> as well.
     * @param tags a set of tags
     * @return the number of tags that are in <code>tags</code>
     * and in <code>this</code>
     */
    public int count( TagSet tags ){
        int count = 0;
        for( Tag tag : tags ){
            if( contains( tag ))
                count++;
        }
        return count;
    }

    public boolean containsOneOf( TagSet tags ){
        for( Tag tag : tags ){
            if( contains( tag ))
                return true;
        }

        return false;
    }

    public Iterator<Tag> iterator(){
        return set.iterator();
    }

    public int size(){
        return set.size();
    }
    
    public TagSet copy(){
        TagSet copy = new TagSet();
        copy.set.addAll( set );
        return copy;
    }

    /**
     * Gets the subset of tags whose {@link Tag#isKey() key property} is
     * set to <code>true</code>. The set generated by this method will not
     * reflect any changes in <code>this</code>, and no change in the result will
     * be forwarded to <code>this</code>.
     * @return a subset of key tags
     */
    public TagSet independentKeySet(){
        TagSet set = new TagSet();
        for( Tag tag : this ){
            if( tag.isKey() )
                set.add( tag );
        }
        return set;
    }

    /**
     * Gets the subset of tags whose {@link Tag#isKey() key property} is
     * set to <code>true</code>. It is not specified whether changes in
     * <code>this</code> reflect in the result, or if changes in the result
     * have sideeffects in <code>this</code>.
     * @return a subset of key tags
     */
    public TagSet keySet(){
        TagSet set = new TagSet();
        for( Tag tag : this ){
            if( tag.isKey() )
                set.add( tag );
        }
        return set;
    }

    public Tag[] toArray(){
    	return set.toArray( new Tag[ size() ] );
    }
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder( "TagSet[tags={");
        boolean first = true;
        for( Tag tag : this ){
            if( first )
                first = false;
            else
                builder.append( ", " );

            builder.append( tag );
        }
        builder.append( "}]" );
        return builder.toString();
    }
}
