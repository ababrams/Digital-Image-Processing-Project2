Program written in Java/Eclipse.

It can be ran through Eclipse with Browser.java holding the main class for configuration. If it doesn't already, eclipse needs opencv added as a User Library.
Running through command line.

{insert commands to run here}


_break_

Background information:

INTER_NEAREST 
Nearest neighbor was our starting point for intensity based interpolation. By using the same pixel spacing as the original image, the desired grid size grows or shrinks to fit exactly over the original image.
This method was by far the easiest as all that is needed is the original size of the image and the times multiplier of the desired size.
Being that simple left nearest neighbor with some draw backs. The interpolation was severely distorted in some cases. 
We also had some instances in which some pixels appeared larger than others in the resized image.

INTER_LINEAR 
Bilinear interpolation gave us better results than nearest neighbor. Using the pixel values of 4 nearest neighbors to estimate intensity for that specific location to make the new image. 
Bilinear reduced some of the visual distortion caused by resizing an image. Overall a better quality than nearest neighbor. 
This felt like the middle ground of the project as it had decent quality and less errors than our other methods.

INTER_CUBIC 
Bicubic interpolation had the best quality so far but felt the most complex. Bicubic uses the pixel values of sixteen nearest neighbors to estimate the intensity at a given location.
The issue with this method is that it is very resource intensive, much more so than bilinear interpolation. Being such a higher demand on resources bicubic could end up clipping a part of the image.
Overall bicubic had the most sharpness but longest run time. 