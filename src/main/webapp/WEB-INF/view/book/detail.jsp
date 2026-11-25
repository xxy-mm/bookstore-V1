<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="../common/statics.jsp" %>
    <title>Book Details - BookStore V1</title>
    <!-- Bulma CSS (v1.0.2) -->

    <style>
        .mock-book-cover {
            max-width: 300px;
            aspect-ratio: 2 / 3;
            margin: 0 auto;
            border-radius: 4px 10px 10px 4px;
            box-shadow: inset 12px 0 18px rgba(0, 0, 0, 0.25), /* Book spine crease */ 0 10px 25px rgba(0, 0, 0, 0.18); /* Drop shadow */
            position: relative;
            overflow: hidden;
        }
    </style>
</head>
<body>

<%@include file="../common/navbar.jsp"%>

<!-- Main Content -->
<section class="section">
    <div class="container">

        <!-- Main Book Card -->
        <div class="box p-5">
            <div class="columns is-variable is-7">

                <!-- Left Column: Book Cover -->
                <div class="column is-4">
                    <div class="mock-book-cover has-background-link-dark has-text-white p-5 is-flex is-flex-direction-column is-justify-content-space-between">

                        <!-- Top: Category Tag & Decorative Line -->
                        <div>
                <span class="tag is-warning is-light has-text-weight-semibold is-uppercase">
                  <c:out value="${book.categoryName}"/>
                </span>
                            <hr class="my-3 has-background-white-ter" style="opacity: 0.25;"/>
                        </div>

                        <!-- Middle: Book Title & Icon -->
                        <div class="my-auto py-3">
                            <p class="title is-3 has-text-white mb-3" style="line-height: 1.2;">
                                <c:out value="${book.title}"/>
                            </p>
                            <p class="is-size-7 has-text-white-ter is-italic">
                                <c:out value="${book.description}"/>
                            </p>
                        </div>

                        <!-- Bottom: Author & Publisher -->
                        <div>
                            <hr class="my-3 has-background-white-ter" style="opacity: 0.25;"/>
                            <p class="is-size-6 has-text-weight-semibold has-text-white">
                                <c:out value="${book.author}"/>
                            </p>
                            <p class="is-size-7 has-text-white-ter">
                                <c:out value="${book.publisher}"/>
                            </p>
                        </div>

                    </div>
                </div>

                <!-- Right Column: Book Metadata & Actions -->
                <div class="column is-8 is-flex is-flex-direction-column is-justify-content-space-between">
                    <div>
                        <!-- Status & Stock Tags -->
                        <div class="tags mb-3">
                            <!-- books.status: 1 = On Sale, 0 = Off Sale -->
                            <span class="tag is-success is-light is-medium">On Sale</span>
                            <!-- books.stock -->
                            <span class="tag is-info is-light is-medium">In Stock (<c:out
                                    value="${book.stock}"/> left)</span>
                        </div>

                        <!-- books.title & books.author -->
                        <h1 class="title is-2 mb-2"><c:out value="${book.title}"/></h1>
                        <p class="subtitle is-5 has-text-grey mb-4">
                            by <span class="has-text-weight-semibold has-text-dark"><c:out
                                value="${book.author}"/></span>
                        </p>

                        <!-- books.price -->
                        <div class="my-4">
                            <span class="is-size-2 has-text-weight-bold has-text-danger"><c:out
                                    value="${book.price}"/></span>
                        </div>

                        <hr/>

                        <!-- Publication Metadata (publisher, isbn, category) -->
                        <div class="columns is-mobile is-multiline is-size-6 mb-4">
                            <div class="column is-6-tablet is-12-mobile py-1">
                                <span class="has-text-grey">Publisher:</span>
                                <span class="has-text-weight-medium ml-1"><c:out value="${book.publisher}"/></span>
                            </div>
                            <div class="column is-6-tablet is-12-mobile py-1">
                                <span class="has-text-grey">ISBN:</span>
                                <span class="has-text-weight-medium ml-1"><c:out value="${book.isbn}"/></span>
                            </div>
                            <div class="column is-6-tablet is-12-mobile py-1">
                                <span class="has-text-grey">Category:</span>
                                <span class="has-text-weight-medium ml-1"><c:out value="${book.categoryName}"/></span>
                            </div>
                        </div>

                        <!-- Add to Cart Form (maps to cart_items: book_id, quantity) -->
                        <form action="${pageContext.request.contextPath}/cart/items" method="post" class="mt-5">
                            <input type="hidden" name="bookId" value="31"/>
                            <div class="field has-addons">
                                <div class="control">
                                    <label class="button is-static" for="quantity">Qty</label>
                                </div>
                                <div class="control">
                                    <input
                                            id="quantity"
                                            name="quantity"
                                            class="input"
                                            type="number"
                                            value="1"
                                            min="1"
                                            max="45"
                                            style="width: 90px;"
                                    />
                                </div>
                                <div class="control">
                                    <button type="submit" class="button is-link">
                                        <span class="icon"><i class="fas fa-cart-plus"></i></span>
                                        <span>Add to Cart</span>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <hr class="my-5"/>

            <!-- Bottom Section: Description (books.description) -->
            <div class="content">
                <h3 class="title is-4">Description</h3>
                <p class="is-size-6 has-text-grey-darker" style="line-height: 1.7;">
                    <c:out value="${book.description}"/>
                </p>
            </div>
        </div>

    </div>
</section>

</body>
</html>
