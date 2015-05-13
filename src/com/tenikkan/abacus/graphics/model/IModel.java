/**
 * 
 */
package com.tenikkan.abacus.graphics.model;

import com.tenikkan.abacus.legacy.Vertex;

/**
 * @author Nicholas Hamilton
 *
 */
public interface IModel
{   
    public Vertex[] getVertices();
    public int[] getIndices();
}
