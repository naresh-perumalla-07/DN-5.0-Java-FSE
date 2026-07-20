import React from 'react';

function BookDetails() {
  const books = [
    { id: 1, title: 'Clean Code', author: 'Robert C. Martin' },
    { id: 2, title: 'Design Patterns', author: 'Gang of Four' },
    { id: 3, title: 'Refactoring', author: 'Martin Fowler' }
  ];

  return (
    <div>
      <h2>Book Details</h2>
      <ul>
        {books.map(book => (
          <li key={book.id}>{book.title} by {book.author}</li>
        ))}
      </ul>
    </div>
  );
}

function BlogDetails() {
  const blogs = [
    { id: 1, title: 'Getting Started with React', date: '2024-01-15' },
    { id: 2, title: 'Understanding Hooks', date: '2024-02-20' },
    { id: 3, title: 'State Management Patterns', date: '2024-03-10' }
  ];

  return (
    <div>
      <h2>Blog Details</h2>
      <ul>
        {blogs.map(blog => (
          <li key={blog.id}>{blog.title} - {blog.date}</li>
        ))}
      </ul>
    </div>
  );
}

function CourseDetails() {
  const courses = [
    { id: 1, name: 'Java FSE', duration: '7 weeks' },
    { id: 2, name: 'React Fundamentals', duration: '4 weeks' },
    { id: 3, name: 'Spring Boot Mastery', duration: '6 weeks' }
  ];

  return (
    <div>
      <h2>Course Details</h2>
      <ul>
        {courses.map(course => (
          <li key={course.id}>{course.name} - {course.duration}</li>
        ))}
      </ul>
    </div>
  );
}

function App() {
  const view = 'books'; // Change to 'blogs' or 'courses'

  // Method 1: if-else
  const renderContent = () => {
    if (view === 'books') return <BookDetails />;
    else if (view === 'blogs') return <BlogDetails />;
    else return <CourseDetails />;
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Blogger App</h1>

      {/* Method 1: Function with if-else */}
      <h3>Using if-else:</h3>
      {renderContent()}

      {/* Method 2: Ternary operator */}
      <h3>Using Ternary:</h3>
      {view === 'books' ? <BookDetails /> : <BlogDetails />}

      {/* Method 3: Logical && */}
      <h3>Using Logical &&:</h3>
      {view === 'books' && <BookDetails />}
      {view === 'blogs' && <BlogDetails />}
      {view === 'courses' && <CourseDetails />}
    </div>
  );
}

export default App;
