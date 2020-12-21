## Development

```bash
# Clone project
git clone https://gitee.com/y_project/RuoYi-Vue

# Enter project directory
cd ruoyi-ui

# Install dependencies
npm install

# It is recommended not to use cnpm to install dependencies directly, as there will be various weird bugs. 
# The slow download speed of npm can be solved by the following operations
npm install --registry=https://registry.npm.taobao.org

# Start service
npm run dev
```

Browser visit http://localhost:80

## Production

```bash
# Build the test environment
npm run build:stage

# Build a production environment
npm run build:prod
```