<h2>Shopping Cart Service</h2>
<p>Repository for the implementation of the shopping cart service.</p>

<h3>Docker Steps:</h3>
    <h4>1. Create a network in which the mysql container and spring boot application will run:</h4>
            <p>docker network create shopping-cart-service</p>
    <h4>2. Create mysql container and add it to the network:</h4>
           <p> docker container run --name mysqldb --network shopping-cart-service -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=shoppingcart -d mysql:latest</p>
    <h4>3. Create the image for the spring boot application:</h4>
            <p>docker image build -t shopping .</p>
    <h4>4. Create spring boot container and add it to the network:</h4>
            <p>docker container run --network shopping-cart-service --name shopping -p 8082:8080 -d shopping</p>
