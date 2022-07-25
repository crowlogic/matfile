module matfile
{
  requires java.desktop;
  requires junit;
  requires commons.math3;
  requires com.sun.jna;
  requires j.text.utils;
  requires jdk.unsupported;

  exports matfile;
  exports matfile.exceptions;
  exports matfile.io;
  exports matfile.matfile.exceptions;
  exports matfile.util;
}