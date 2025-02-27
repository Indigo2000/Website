import * as React from 'react';
import stories from './stories';
import { FaGithub, FaLinkedin, FaFilePdf } from 'react-icons/fa'; // Import icons
import { SiCodesignal } from 'react-icons/si'; // Import CodeSignal icon

const welcome = {
  greeting: 'Will Irvine - Software Developer',
  title: 'MSc in Computer Science Coursework'
};

const useStorageState = (key, initialState) => {
  const [value, setValue] = React.useState(
    localStorage.getItem(key) || initialState
      );

  React.useEffect(() => {
    localStorage.setItem(key, value);
  }, [value, key]);
  return [value, setValue]
};


const App = () => {
  
  const [searchTerm, setSearchTerm] = useStorageState('search', '');

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);

  };
  
  // const sections = stories || [];
  const filteredStories = stories.map((section) => ({
    ...section,
  items: section.items ? section.items.filter((story) =>
    story.title.toLowerCase().includes(searchTerm.toLowerCase())
  )
  : [],
})).filter(section => section.items.length > 0); // Remove empty sections

  return (
    <div
    style={{
      backgroundImage: "url('/files/background.JPG')",
      backgroundSize: "cover",
      backgroundPosition: "center",
      backgroundAttachment: "fixed", // Ensures background stays put when scrolling
      minHeight: "100vh", // Ensures full-page background but allows scrolling
      padding: "20px", // Adds some space inside the page
    }}
  >
       <header style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '10px' }}>
      <h1>
        {welcome.greeting}</h1>
              {/* Social Links with Icons */}
              <div style={{ fontSize: '24px' }}>
          <a href="/files/MyCV.pdf" download title="Download CV" style={{ marginRight: '10px' }}>
            <FaFilePdf />
          </a>
          <a href="https://github.com/Indigo2000" target="_blank" rel="noopener noreferrer" title="GitHub" style={{ marginRight: '10px' }}>
            <FaGithub />
          </a>
          <a href="https://www.linkedin.com/in/will-irvine-12675a236" target="_blank" rel="noopener noreferrer" title="LinkedIn" style={{ marginRight: '10px' }}>
            <FaLinkedin />
          </a>
          <a href="https://codesignal.com/learn/profile/cm5uvkicx001942mb7p3z8knt" target="_blank" rel="noopener noreferrer" title="CodeSignal">
            <SiCodesignal />
          </a>
        </div>
      </header>
        <hr />
        <h1>
        {welcome.title}</h1>
        <InputWithLabel
        id="search"
        label=""
        value={searchTerm}
        isFocused
        onInputChange={handleSearch} 
        >
          <strong>Search:</strong>
      </InputWithLabel>
      

      {Array.isArray(filteredStories) && <List sections={filteredStories} />}
    </div>
  );
};

const List = ({ sections }) => (
  <div>
    {sections.map((section, index) => (
      <div key={index}>
        <h2>{section.heading}</h2> {/* Render section heading */}
        {section.items && section.items.length > 0 ? (
    <ul>
      {section.items.map((item) => (
          <Item key={item.objectID} item={item} />
      )
    )}
  </ul>
        ) : (
          <p>No Matching Stories</p>
        )}
  </div>
      ))}
  </div>
  );

const Item = ({item, onRemoveItem}) => {
  const handleRemoveItem = () => {
    onRemoveItem(item);
  };
  
  return (
  <li>
    <span>
      <a href={item.url} target="_blank" rel="noopener noreferrer">
         {item.title}</a>
    </span>
  </li>
);
};

const InputWithLabel =({id, label, value, type = 'text', onInputChange, isFocused, children}) => {

  const inputRef = React.useRef();

  React.useEffect(() => {
    if (isFocused && inputRef.current) {
      inputRef.current.focus();
    }
  }, [isFocused]);  
  
  return (
    <>
      <label htmlFor={id}>{children}{label} </label>
      &nbsp;
      <input
        ref={inputRef} 
        id={id}
        type={type}
        value={value}
        onChange={onInputChange}
        />
    </>
  );
}
export default App