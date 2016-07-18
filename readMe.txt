Author: Sydney Lyon
Version: 1.0 7/18/2016

INTRO
--------
This program was created to stitch together flows from netflow data that has been collected in
CSV form.

FUNCTIONALITY
--------------
This program should make a copy of the original files in the desired new location.  Flows will
then be stitched together if they have the same Source IP, Destination IP, Source AS,
Destination AS, and Protocol and close Start/End times.

The program is currently set up to work with files that have been seperated by month,
IE: ~/.../year/month/date.csv or for example ~/user/home/flows/2016/01/30.csv
This is because the program parses the path as a string in order to rename the file in the new
location. If this is not how your file structure works, the makeNewPath() method can be changed
to rename it as you like.  The FolderWalker program assumes that it has been given a directory
that contains directories that in turn only contain CSV files.

Once files have been copied and renamed, FileWalker goes through the files in the new directory
and stitches them together.

NECESSARY FILES
---------------
FileWalker
FolderWalker
Reader
Stitcher
Writer

USE
-----
New FolderWalker should be created with three String inputs: source directory, destination 
directory, and the type of file seperator for your system ("\\", "\/").  You will also need to
use the method FolderWalker.run().  If the naming structure is as described in the FUNCTIONALITY
section, all files will be copied and stitched.

If you already all the files that need to be stitched in one directory, you can simply use the
FileWalker class.  To do this, create a new FileWalker with one String input: source directory.
After using the method FileWalker.run(), files will be stitched together.

WARNING: If you only use FileWalker, the flow files will be overwritten when they are stitched
together.  It is recommended that you do not use this on your original flow files, but create a
copy and stitch those.
