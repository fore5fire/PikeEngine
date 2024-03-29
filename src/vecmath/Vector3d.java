/*
   Copyright (C) 1997,1998,1999
   Kenji Hiranabe, Eiwa System Management, Inc.

   This program is free software.
   Implemented by Kenji Hiranabe(hiranabe@esm.co.jp),
   conforming to the Java(TM) 3D API specification by Sun Microsystems.

   Permission to use, copy, modify, distribute and sell this software
   and its documentation for any purpose is hereby granted without fee,
   provided that the above copyright notice appear in all copies and
   that both that copyright notice and this permission notice appear
   in supporting documentation. Kenji Hiranabe and Eiwa System Management,Inc.
   makes no representations about the suitability of this software for any
   purpose.  It is provided "AS IS" with NO WARRANTY.
*/
package vecmath;

import java.io.Serializable;

/**
  * A 3 element vector that is represented by double precision floating point
  * x,y,z coordinates. If this value represents a normal, then it should be
  * normalized.
  * @version specification 1.1, implementation $Revision: 1.8 $, $Date: 1999/10/05 07:03:50 $
  * @author Kenji hiranabe
  */
public class Vector3d extends Tuple3d implements Serializable {
/*
 * $Log: Vector3d.java,v $
 * Revision 1.8  1999/10/05  07:03:50  hiranabe
 * copyright change
 *
 * Revision 1.8  1999/10/05  07:03:50  hiranabe
 * copyright change
 *
 * Revision 1.7  1999/03/04  09:16:33  hiranabe
 * small bug fix and copyright change
 *
 * Revision 1.6  1998/10/14  00:49:10  hiranabe
 * API1.1 Beta02
 *
 * Revision 1.5  1998/04/10  04:52:14  hiranabe
 * API1.0 -> API1.1 (added constructors, methods)
 *
 * Revision 1.4  1998/04/09  08:18:15  hiranabe
 * minor comment change
 *
 * Revision 1.3  1998/04/09  07:05:18  hiranabe
 * API 1.1
 *
 * Revision 1.2  1998/01/05  06:29:31  hiranabe
 * copyright 98
 *
 * Revision 1.1  1997/11/26  03:00:44  hiranabe
 * Initial revision
 *
 */


    /**
	 * 
	 */
	private static final long serialVersionUID = 6976074829889438230L;
	public static final Vector3d X_UNIT_VECTOR = new Vector3d(1,0,0);
	public static final Vector3d Y_UNIT_VECTOR = new Vector3d(0,1,0);
	public static final Vector3d Z_UNIT_VECTOR = new Vector3d(0,0,1);
	public static final Vector3d ZERO_VECTOR = new Vector3d(0,0,0);


	/**
      * Constructs and initializes a Vector3d from the specified xyz coordinates.
      * @param x the x coordinate
      * @param y the y coordinate
      * @param z the z coordinate
      */
    public Vector3d(double x, double y, double z) {
	super(x, y, z);
    }

    /**
      * Constructs and initializes a Vector3d from the specified array of length 3.
      * @param v the array of length 3 containing xyz in order
      */
    public Vector3d(double v[]) {
    	super(v);
    }
    
    public Vector3d(Tuple3i v) {
    	super(v);
    }
    
    /**
      * Constructs and initializes a Vector3d from the specified Vector3f.
      * @param v1 the Vector3d containing the initialization x y z data
      */
    public Vector3d(Vector3f v1) {
	super(v1);
    }

    /**
      * Constructs and initializes a Vector3d from the specified Vector3d.
      * @param v1 the Vector3d containing the initialization x y z data
      */
    public Vector3d(Vector3d v1) {
	super(v1);
    }

    /**
      * Constructs and initializes a Vector3d from the specified Tuple3d.
      * @param t1 the Tuple3d containing the initialization x y z data
      */
    public Vector3d(Tuple3d t1) {
	super(t1);
    }

    /**
      * Constructs and initializes a Vector3d from the specified Tuple3f.
      * @param t1 the Tuple3f containing the initialization x y z data
      */
    public Vector3d(Tuple3f t1) {
	super(t1);
    }

    /**
      * Constructs and initializes a Vector3d to (0,0,0).
      */
    public Vector3d() {
	super();
    }
    
    private static double PI_OVER_2 = Math.PI/2;
    
    /**
     * Sets the value of this vector to the Euler angle representation of the quaternion argument.
     * @param q1 the quaternion
     */
    public void set(Quat4d q1) {
    	double test = q1.x*q1.y + q1.z*q1.w;
    	if (test > 0.499) { // singularity at north pole
    		y = 2 * Math.atan2(q1.x,q1.w);
    		z = PI_OVER_2;
    		x = 0;
    	}
    	else if (test < -0.499) { // singularity at south pole
    		y = -2 * Math.atan2(q1.x,q1.w);
    		x = - PI_OVER_2;
    		z = 0;
    	}
    	else {
    		double sqx = q1.x*q1.x;
	        double sqy = q1.y*q1.y;
	        double sqz = q1.z*q1.z;
	        y = Math.atan2(2*q1.y*q1.w-2*q1.x*q1.z , 1 - 2*sqy - 2*sqz);
	    	x = Math.asin(2*test);
	    	z = Math.atan2(2*q1.x*q1.w-2*q1.y*q1.z , 1 - 2*sqx - 2*sqz);
    	}
    }

    /**
      * Sets this vector to be the vector cross product of vectors v1 and v2.
      * @param v1 the first vector
      * @param v2 the second vector
      */
    public final void cross(Vector3d v1, Vector3d v2) {
	// store on stack once for aliasing-safty
	// i.e. safe when a.cross(a, b)
	set(
	    v1.y*v2.z - v1.z*v2.y,
	    v1.z*v2.x - v1.x*v2.z,
	    v1.x*v2.y - v1.y*v2.x
	    );
    }
    

    /**
      * Sets the value of this vector to the normalization of vector v1.
      * @param v1 the un-normalized vector
      */
    public final void normalize(Vector3d v1) {
	set(v1);
	normalize();
    }

    /**
      * Normalizes this vector in place.
      */
    public final void normalize() {
	double d = length();

	// zero-div may occur.
	x /= d;
	y /= d;
	z /= d;
    }

    /**
      * Computes the dot product of the this vector and vector v1.
      * @param  v1 the other vector
      */
    public final double dot(Vector3d v1) {
	return x*v1.x + y*v1.y + z*v1.z;
    }


    /**
      * Returns the squared length of this vector.
      * @return the squared length of this vector
      */
    public final double lengthSquared() {
	return x*x + y*y + z*z;
    }

    /**
      * Returns the length of this vector.
      * @return the length of this vector
      */
      public final double length() {
	  return Math.sqrt(lengthSquared());
      }

      

    /**
      * Returns the angle in radians between this vector and
      * the vector parameter; the return value is constrained to the
      * range [0,PI].
      * @param v1  the other vector
      * @return the angle in radians in the range [0,PI]
      */
    public final double angle(Vector3d v1) {
		// return (double)Math.acos(dot(v1)/v1.length()/v.length());
		// Numerically, near 0 and PI are very bad condition for acos.
		// In 3-space, |atan2(sin,cos)| is much stable.
	
		double xx = y*v1.z - z*v1.y;
		double yy = z*v1.x - x*v1.z;
		double zz = x*v1.y - y*v1.x;
		double cross = Math.sqrt(xx*xx + yy*yy + zz*zz);
	
		return Math.abs(Math.atan2(cross, dot(v1)));
    }
    
    public float distance(Vector3d v1) {
    	Vector3d difference = new Vector3d();
    	difference.sub(this, v1);
    	return (float)Math.sqrt(difference.x * difference.x + difference.y * difference.y + difference.z * difference.z);
    }
    
    public double innerProduct(Vector3d v) {
    	return x * v.x + y * v.y + z * v.z;
    }
    
    public final Vector3i toVector3i() {
    	return new Vector3i(this);
    }
}
