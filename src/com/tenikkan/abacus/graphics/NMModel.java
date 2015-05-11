/**
 * 
 */
package com.tenikkan.abacus.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tenikkan.abacus.math.Vector4f;
import com.tenikkan.abacus.util.Console;

/**
 * @author Nicholas Hamilton
 *
 */
public class NMModel implements IModel
{
    private Vertex[] vertices;
    private int[] indices;
    
    public NMModel(String filename) 
    {
        try 
        {
            float texX[] = new float[1];
            float texY[] = new float[1];
            Vector4f vec[] = new Vector4f[1];
            int indVec[] = new int[1];
            int indTex[] = new int[1];
            
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            
            String line;
            while((line = reader.readLine()) != null) 
            {
                String[] tokens = line.split(" ");
                if(tokens.length == 0 || tokens[0].equals(";")) continue;
                
                int index;
                switch(tokens[0]) 
                {
                case "b": // tex vert ind
                    
                    int texLen = toInt(tokens[1]);
                    int vertLen = toInt(tokens[2]);
                    int indLen = toInt(tokens[3]);
                    
                    texX = new float[texLen];
                    texY = new float[texLen];
                    vec = new Vector4f[vertLen];
                    indVec = new int[indLen*3];
                    indTex = new int[indLen*3];
                    
                    break;
                case "v": 
                    
                    index = toInt(tokens[1]);
                    float x = toFloat(tokens[2]);
                    float y = toFloat(tokens[3]);
                    float z = toFloat(tokens[4]);
                    vec[index] = new Vector4f(x, y, z, 1.0f);
                    
                    break;
                case "t": 
                    
                    index = toInt(tokens[1]);
                    float tx = toFloat(tokens[2]);
                    float ty = toFloat(tokens[3]);
                    texX[index] = tx;
                    texY[index] = ty;
                    
                    break;
                case "i":
                    
                    index = toInt(tokens[1]);
                    indVec[index*3    ] = toInt(tokens[2]);
                    indVec[index*3 + 1] = toInt(tokens[3]);
                    indVec[index*3 + 2] = toInt(tokens[4]);
                    indTex[index*3    ] = toInt(tokens[5]);
                    indTex[index*3 + 1] = toInt(tokens[6]);
                    indTex[index*3 + 2] = toInt(tokens[7]);
                    
                    break;
                }
            }
            
            reader.close();
            
            genData(texX, texY, vec, indTex, indVec);
            
        } catch(IOException e) 
        {
            Console.outln("Could not load NM File \"" + filename + "\"!", Console.WARNING);
        }
    }
    
    private void genData(float tx[], float ty[], Vector4f vec[], int indTex[], int indVec[]) 
    {
        List<Vertex> vert = new ArrayList<Vertex>();
        List<Integer> ind = new ArrayList<Integer>();
        
        for(int i = 0; i < indVec.length; i++) 
        {
            int index = getVertexIndex(vert, vec[indVec[i]], tx[indTex[i]], ty[indTex[i]]);
            if(index == -1) 
            {
                vert.add(new Vertex(vec[indVec[i]], new Vector4f(tx[indTex[i]], ty[indTex[i]], 0.0f, 0.0f)));
                ind.add(vert.size() - 1);
            } else 
            {
                ind.add(index);
            }
        }
        
        indices = new int[ind.size()];
        for(int i = 0; i < indices.length; i++) 
            indices[i] = ind.get(i).intValue();
        vertices = (Vertex[])vert.toArray(new Vertex[vert.size()]);
    }
    
    private int getVertexIndex(List<Vertex> vert, Vector4f pos, float tx, float ty) 
    {
        for(int i = 0; i < vert.size(); i++) 
        {
            Vertex v = vert.get(i);
            if(v.getPosition().equals(pos) && v.getTexCoordX() == tx && v.getTexCoordY() == ty)
                return i;
        }
        
        return -1;
    }
    
    @Override
    public Vertex[] getVertices()
    {
        return vertices;
    }

    @Override
    public int[] getIndices()
    {
        return indices;
    }   
    
    private int toInt(String s) 
    {
        return Integer.parseInt(s);
    }
    
    private float toFloat(String s) 
    {
        return (float)Double.parseDouble(s);
    }
}
