## Welcome

Welcome to this mockup company-product sceneria, here are the basic rules:
1. In real world, companies are not independent while sometimes one can have its own sub-company(ies).
2. To survive in the market, each company has to release its own product(s).
3. Also market has some feedback to classify these products.

Now our data are built based on these:

- **Company Information**
```json
{
    "id": "c1",
    "name": "Company One"
}
```
***<sup>The company with name id of 'c1' is named as 'Company One'</sup>***

- **Company Relationship**
```json
{
    "parentId": "c1",
    "subcoordinateId": "c2"
}
```
***<sup>This means that company with id of 'c2' is a sub company of that with id of 'c1'</sup>***

- **Product**
```json
{
    "id": "p1",
    "name": "Product 1",
    "companyId": "c1",
    "price": 199.99,
    "classification": "B"
}
```
***<sup>Product with ID of 'p1' and name of 'Product 1' belongs to company with ID 'c1', while the price of it is 199.99 and classification is 'B' level based on the feedback from market</sup>***

---
**Now everything starts ...**

---
## Requirement

The final target is to provide a **filtered** and **sorted** product list based on the priority criterias.

#### 1. Filter
To filter products, blacklisted products and blaclisted companies are provided:
- **blacklisted products** - array of product IDs
```json
["p1", "p4"]
```
- **blacklisted companies** - array of company IDs:
```json
["c2", "c5"]
```

**Rules**

**1. By blacklisted products**
All the products in the blacklisted products should be filtered out from result

**2. By blacklisted companies**
The products should be filtered out if they belong to the companies in the blacklisted companies or the sub-companies of the blacklisted companies.

For example, suppose the products and companies are as below:
- **products**
```json
[
    {
        "id": "p1",
        "companyId": "c1",
        "price": 100,
        "classification": "S"
    },
    {
        "id": "p2",
        "companyId": "c2",
        "price": 150,
        "classification": "A"
    },
    {
        "id": "p3",
        "companyId": "c3",
        "price": 50,
        "classification": "F"
    },
    {
        "id": "p4",
        "companyId": "c4",
        "price": 80,
        "classification": "A"
    }
]
```
- **company relationship**
```json
{
    "parentId": "c2",
    "subcoordinateId": "c3"
}
```
- **blacklisted products**
```json
["p4"]
```
- **blacklisted companies**
```json
["c2"]
```

First the product with ID of 'p4' will be filtered out since it is in the blacklisted products. Then since company with ID of 'c2' is in blacklist while that with ID of 'c3' is its sub-company, all products of them should be filtered out. Finally the result should only contain product with ID of 'p1', as below:
```json
[
    {
        "id": "p1",
        "companyId": "c1",
        "price": 100,
        "classification": "S"
    }
]
```

#### 2. Sort
To sort the products, toplisted products and general priorities are provide:

- **toplisted products** - array of product IDs<br/>
    ***<sub>These products should always be put to the first positions of the result list.</sub>***
    ```json
    ["p5", "p2"]
    ```
    

- **priorities**
    General data format for priority (sorting criteria)
    ```json
    {
        "rank": "<rank_number>",
        "type": "<priority_type>",
        "source": "<source_for_priority>",
        "dir": "<sort_direction>",
    }
    ```
    ***<sup>Note: item with lower rank number has higher priority</sup>***

    There are totally 3 types of priorities:
    - **PRICE**
        ```json
        {
            "rank": "<rank_number>",
            "type": "PRICE",
            "dir": "ASC or DESC"
        }
        ```

    - **CLASSIFICATION**<br/>
        ***<sub>There are five classifications with order from low to high are: F, C, B, A, S</sub>***
        ```json
        {
            "rank": "<rank_number>",
            "type": "CLASSIFICATION",
            "dir": "ASC or DESC"
        }
        ```
        
    - **COMPANY**<br/>
        ***<sub>This type means whether a product belongs to the company with ID in the source field or the sub-companies of it</sub>***
        ```json
        {
            "rank": "<rank_number>",
            "type": "CLASSIFICATION",
            "source": "<company_id>"
        }
        ```
    Let's take an example, suppose that the priority list is shown as below. 
    ```json
    [
        {
            "rank": 2,
            "type": "COMPANY",
            "source": "c2"
        },
        {
            "rank": 1,
            "type": "PRICE",
            "dir": "ASC"
        },
        {
            "rank": 3,
            "type": "CLASSIFICATION",
            "dir": "DESC"
        }
    ]
    ```
    These priorities means that the products should be sorted with following criterias:
    - i. Sort the products based on price in asceniding order firstly since it is ranked as 1;
    - ii. For products with the same price, the ones belongs to company 'C2' or its sub-companies have higher priority;
    - iii. Then for products having same price and all belongs to company 'c2' (including its sub-companies) or those are not from this company hierarchy, sort them based on classification in descending order (from S to F);

## Tasks

Here are the tasks to acheive these two requirements.

##### 1. [CompanyController#getById](src/main/java/com/qima/product/controller/CompanyController.java) - call created API (```/companies...```) to verify
Implement a RESTful API to get a company by company ID

##### 2. [CompanyDao#initCompanies](src/main/java/com/qima/product/dao/CompanyDao.java) - call ```/companies``` to verify
Fill parent field in all company entity with the relation data. Result of the company list should be in format as below:
```json
[
    {
        "id": "c1",
        "name": "Company Two",
        "parent": null
    },
    {
        "id": "c2",
        "name": "Company Two",
        "parent": {
            "id": "c1",
            "name": "Company One",
            "parent": null
        }
    }
]
```

##### 3. [ProductService#getAllPrioritized](src/main/java/com/qima/product/service/ProductService.java) - call ```/products/prioritization``` to verify
Filter the existing products based on the blacklisted products and companies

##### 4. [ProductService#sortProducts](src/main/java/com/qima/product/service/ProductService.java) - call ```/products/prioritization``` to verify
This is the final **Big Boss** to sort the products based on the priorities. Be carefull that the priorities are not fixed, it means if the priority data were changed, no code changes should be done in order to get the new sorted products.