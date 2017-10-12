# lodsurfer-api
Web API for LOD Surfer

This is a web api for a search system called LOD Surfer.

## How to acess Web APIs

- Get a list of SPARQL endpoints (that have a certain class {class1} with the parameter class1). If the parameter class1 is omitted, all SPARQL endpoints are listed.
  - /eplist/?class1={class1}
 
- Get a list of classes (that have a path from a certain class {class1} with the parameter class1). If the parameter class1 is omitted, all classes are listed. 
  - /clist/?class1={class1}
 
- Get a list of paths from class1 to class2 (with {filter1}, {filter2}, ...). If the parameter filter is omitted, all paths are listed. Note that each filter is a pair of a class C and a boolean b. If the boolean b is true, each path should go through the class C. If the boolean b is false, each path must not go through the class C.
  - /path/?class1={class1}&class2={class2}&filter=[{filter1}, {filter2},....]

- Get a set of triple patterns with information of SPARQL endpoints from a path. The parameter instance can be omitted. 
  - /tps/?path={path}&instances=[{instance1},{instance2},...]
 
- Get a federated SPARQL query from a path. The parameter instance can be omitted.
  - /sparql/?path={path}&instances=[{instance1},{instance2},...]
