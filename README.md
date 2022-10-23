# NumberSequenceProcessing

The service allows you to process numeric data from a txt file.

The following operations are currently available:
1. maximum number in the file;
2. minimum number in the file;
3. median;
4. arithmetic mean value;
5. the longest sequence of consecutive numbers, which is increasing;
6. the longest sequence of consecutive numbers, which is decreasing.

The results are output in json or xml formats. The format can be changed by adding header `accept:application/<desired format type>`

A file can be passed as a path to it in a request or in binary form in a post request

All operations with files are cached, which speeds up the output

Available file transfer paths:

`<path_to_your_server>/api/` - Passing files by path

`<path_to_your_server>/api/multipart-file/` - File transfer in a post request

## Examples of requests:

### File transfer by path, specifying the operation in the body of the request:
```
link: <path_to_your_server>/api/perform-operation

body: 
{
    "operation": "MAX_VALUE",
    "filePath": "<path_to_file>"
}
```
returns - maximum value in the file:
```
{
    "status": "OK",
    "message": "successful request",
    "data": 49999978
}
```

### File transfer by path, the operation is specified in the link:
```
link: <path_to_your_server>/api/get-mean

body: 
{
    "filePath": "<path_to_file>"
}
```
returns - arithmetic mean:
```
{
    "status": "OK",
    "message": "successful request",
    "data": 7364.418442641844
}
```

### File transfer in post request, the operation is transferred in the body of the request:
```
link: <path_to_your_server>/api/multipart-file/perform-operation
```
![image](https://user-images.githubusercontent.com/55204274/197420748-daa8ccff-322c-466f-b034-edbbc3bb8b85.png)

returns - minimum value: 
```
{
    "status": "OK",
    "message": "successful request",
    "data": -49999996
}
```

### File transfer in post request, operation in link:
```
link: <path_to_your_server>/api/multipart-file/get-increasing-sequence
```
![image](https://user-images.githubusercontent.com/55204274/197420816-4882e32b-ff0d-424b-9465-c1656be41065.png)

returns - longest sequences of consecutive numbers, which is increasing
```
{
    "status": "OK",
    "message": "successful request",
    "data": [
        [
            -48190694,
            -47725447,
            -43038241,
            -20190291,
            -17190728,
            -6172572,
            8475960,
            25205909,
            48332507,
            48676185
        ]
    ]
}
```
Note: A list within a list is used, because several of the longest sequences can be found, for example:
```
{
    "status": "OK",
    "message": "successful request",
    "data": [
        [
            1,
            3,
            7
        ],
        [
            1,
            2,
            3
        ]
    ]
}
```
