# lodsurfer-api
Web API for LOD Surfer

This is a web api for a search system called LOD Surfer.

## How to acess Web APIs

- Get a list of SPARQL endpoints (that have a certain class {class1} with the parameter class1). If the parameter class1 is omitted, all SPARQL endpoints are listed.
  - /eplist/?class1={class1}
 
- Get a list of classes (that have a path from a certain class {class1} with the parameter class1). If the parameter class1 is omitted, all classes are listed. 
  - /clist/?class1={class1}
 
- Get a list of paths from class1 to class2. 
  - /path/?class1={class1}&class2={class2}
  
- Get a federated SPARQL query from a path. 
  - /sparql/?path={path}
  
