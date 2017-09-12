# lodsurfer-api
Web API for LOD Surfer

This is a web api for a search system called LOD Surfer.

## How to acess Web APIs

- Get a list of SPARQL endpoints (having a certain class {class1}). If {class1} is omitted, all SPARQL endpoints are listed.
  - /eplist/{class1}
 
- Get a list of classes (having a path from a certain class {class1}). If {class1} is omitted, all classes are listed.
  - /clist/{class1}
 
- Get a list of paths from class1 to class2 (with filter1, filter2, ...). If {filter1, filter2, ...} are omitted, all paths are listed.
  - /path/{class1}+{class2}+{filter1,...}
 
 
