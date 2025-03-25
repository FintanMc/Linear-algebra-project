How it works: 

Cubes: 

    Each Cube is defined by a position vector in R3, a scale factor in R1, and an orientation that is described using a quaternion. 
    When the program loads, 3 random unit quaternions are initialised for each cube.
    Each frame after that, the quaternions angles are incremented (scalar part is cos(angle/2), vector part is multiplied by sin(angle/2)).
    The 3 quaternions are then multiplied together to form the Cubes orientation at a given frame, which is why the Cubes appear to spin randomly. 

Vertices of cubes: 

    The position of the vertices of each Cube is calculated by first considering a Cube with face normals that are aligned with the 3 Euclidean axes, with a center of (0,0,0) and with the scale factor of the desired Cube.
    Considering the vertex position as a line (vector) between the vertex and the center of the Cube allows for a rotation about the center of the Cube by the Cube's orientation quaternion. 
    Once each vertex is rotated, they are all translated by the Cubes position vector. 

3d->2d Projection: 

    Each vertex position is now in the right place in 3 dimensions but can't be displayed properly as our screens are 2 dimensions. 
    A “Camera” object is introduced which is described as a position vector in R3, and a yaw and pitch angle value in R1. (roll angle not needed) 
    To project a point from R3 to R2 relative to the camera, they undergo the following linear transformations: M(C(v)) 
        1: Change of basis from standard to camera: 
                                    C(v) = P*Y*(v-c) 

            P: the 3x3 matrix that un-rotates the point by the cameras pitch 
            Y: the 3x3 matrix that un-rotates the point by the cameras yaw 
            v: vertex position 
            c: camera position 

        2: Perspective projection: 

                                        M(v) = S*v 

            S: the 3x3 diagonal matrix with s/(v.z) + t as the x and y entry, and 1 as the z entry, where s is the projection factor, t is an offset and v.z is the z component of v. 
            v: vertex position (after localising to camera) 

    The screen coordinates of the vertex will be the x and y components of M(C(v)), and the depth of the point will be the z component (useful, but not necessarily needed) 

    

Rendering the Faces of Cubes: 

    Each face of a Cube is defined by 4 vertices and a normal vector in R3 (the normal of the plane created by the 4 vertices, scaled to point outwards from the center and normalised) 
    A face is only displayed if the result of the dot product between the normal vector and the direction vector of the camera is less than 0, i.e. they are negatively correlated or pointing in opposite directions. This rule is referred to as backface culling in 3d graphics. 
    If they are pointing in opposite directions, a polygon is drawn to the screen between the screen positions of the 4 vertices 