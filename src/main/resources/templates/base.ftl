[#ftl]
<!DOCTYPE html>
<html>
<head>
    <meta charset="${charset}">
    <title>${windowTitle} | ${title}</title>
[#if stylesheet??]
    <link rel="stylesheet" href="${stylesheet}">
[/#if]
</head>
<body>
${content}
</body>
</html>